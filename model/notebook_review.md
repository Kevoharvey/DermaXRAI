# Full notebook review — 27 October 2026

Reviewed all 45 cells in `testing_2.ipynb`, the saved settings and histories, actual
training/validation manifests, and the complete execution path. The model is learning;
the evidence does not suggest that ResNet-18 or the loss function is fundamentally
broken. Several training-control and reporting problems were verified and corrected.

## Findings and fixes

### 1. Early stopping could prevent the reduced learning rate from being tried

The notebook had `PATIENCE=3` and scheduler patience 2. PyTorch reduces the rate
after the number of bad epochs exceeds its patience, so the third non-improving
epoch reduced the rate and stopped training at the same time. The model never got
an epoch at that reduced rate. This was reproduced with an actual optimizer and
scheduler, rather than inferred only from the parameter names.

The scheduler now has `LR_PATIENCE=1`: it reduces after two non-improving epochs,
leaving an epoch before early stopping at three. A configuration check requires
`PATIENCE >= LR_PATIENCE + 2`. Scheduler and early stopping use the same absolute
macro-F1 improvement threshold. The best checkpoint still uses the highest actual
macro F1, including smaller improvements. Learning rates actually used each epoch
are recorded in the history.

[PyTorch scheduler semantics](https://docs.pytorch.org/docs/2.14/generated/torch.optim.lr_scheduler.ReduceLROnPlateau.html).

### 2. Rerunning cells could mix old model state with a fresh epoch counter

Previously, model/optimizer initialization and the training loop were separate.
Rerunning only training reset the history and epoch counter while retaining trained
weights and optimizer state. The fixed output folder also let new manifests and
histories overwrite earlier runs; old final reports could remain beside a new run.

Training now initializes a fresh model and optimizer together. Each settings-cell
execution creates a new timestamped output folder. A marker rejects a second start
in the same folder, and manifest hashes/configuration checks catch changes to rows,
class order, or sampling settings after splitting. Dataset instances own a copy of
their class mapping instead of looking up a mutable notebook global for each item.

For a fresh experiment, restart the kernel and run all cells in order. This is not
a resume-training implementation. For prediction-only use, select an existing model
with `CHECKPOINT_TO_LOAD` and set `RUN_TRAINING=False`.

### 3. Silent validation and ambiguous progress labels looked like a stall

With full mode enabled, 36,777 images at batch size 32 produce 1,150 training
batches, including the final 9-image batch. Validation then processes another
9,195 images in 288 batches. It previously had no progress bar.

Both phases now show batch-labeled progress, and the notebook prints image and batch
counts. `FAST_MODE=False` continues to mean full training. `ROTATE_TRAINING_IMAGES`
only has an effect in fast mode, which is now explicitly stated when settings run.

### 4. Regularization settings were scattered and incompletely recorded

Dropout was hard-coded in model creation, and weight decay was repeated in two
optimizer definitions. Changing only the warmup optimizer would silently lose that
change when fine-tuning began. These values and scheduler settings were absent from
the saved experiment settings.

Both optimizers now share `WEIGHT_DECAY`; dropout, all three learning rates, warmup,
stopping settings, class weights, and the PyTorch version are recorded. The new default
weight decay is **0.001**, the previously proposed experiment, with dropout **0.25**.
The earlier value was 0.0001. This is a hypothesis to test for better generalization,
not a proven fix or a guarantee of higher accuracy. Use the same data mode and full
validation report when comparing it with prior runs. Scheduling fixes also affect
the comparison, so it does not isolate weight decay alone.

[PyTorch AdamW](https://docs.pytorch.org/docs/2.14/generated/torch.optim.AdamW.html).

### 5. The confusion-matrix plot hid five diagnoses

The exported matrix contained all 20 classes, but the plotted matrix selected only
the 15 most common. Errors involving the other five could be missed visually.
The plot now includes and saves all 20 classes. Validation loss is also included in
the learning curves. Accuracy is saved in a separate summary instead of appearing
as a misleading classification-report row with accuracy repeated under support.

### 6. Official predictions were unnecessarily slow

The official prediction cell performed a separate forward pass for each image.
It now predicts batches and writes results back by original row position. Missing
images retain a status and an empty prediction, and output order is checked. Tests
cover missing rows and the checkpoint's class ordering.

## What checked out

- The current full split has 36,777 training and 9,195 validation images across 20
  classes. Rebuilding it with the notebook produced identical full manifests.
- No training/validation source-case groups or exact canonical-pixel hashes overlap.
  The training partition contains no duplicate canonical-pixel hashes.
- Label indices match output columns, and raw logits correctly feed cross-entropy.
  There is no incorrect softmax before the training loss.
- The classifier warms up first; fine-tuning allows gradients only in the last
  feature block and classifier. Batch-normalization running statistics stay frozen.
- Validation uses evaluation mode without gradients or training augmentation.
  The best saved weights are restored for the final report.
- Image loading applies orientation, valid ICC color conversion, and correct
  transparency handling. Feature scaling is fitted on training only.
- Captions/source metadata are not model inputs. The official CSV lacks diagnosis
  targets, so no unsupported official-test accuracy is reported.

## Remaining data and evaluation limits

The latest full-data history inspected in the v4 folder reaches 84.2% augmented
training accuracy and 50.5% validation accuracy by epoch 12, with macro F1 0.4225.
Validation loss rose from about 1.71 at epoch 5 to 2.20 at epoch 12. This is consistent
with overfitting and increasingly confident validation errors. It does not identify
one unique cause or establish that a larger network will help.

Class counts range from 415 to 7,661 training images. Inverse-square-root loss
weights provide some compensation, but equal performance across diagnoses is not
guaranteed. Video frames also provide less independent information than the same
number of unrelated cases. Source/case grouping is a proxy, not proof of patient
independence; exact hashes do not catch all recompressed/cropped near duplicates.

Structural cleaning does not establish medical label correctness. The earlier visual
review found an image of feet/ankles whose caption describes a face/cheek. It remains
flagged in `student_v5_rotating_20/verified_content_issues.csv` and the training review
queue. No replacement diagnosis was invented. This review did not medically adjudicate
all training images or estimate the dataset-wide annotation-error rate.

The final full validation report is internal validation. It includes the examples
used for checkpoint selection; it is not an independent final test. Fast mode and
full mode use different per-epoch validation distributions, so compare their final
full-validation reports rather than comparing individual epoch accuracy directly.

## Verification completed

The 11 regression tests passed. They exercise scheduler timing, both optimizers,
batch counts, gradients/frozen layers, stable class mapping, unweighted validation,
sample rotation, color conversion, checkpoint preservation on accidental reruns,
and batched prediction row/class alignment.

The full data-preparation path was rerun against the actual cached manifests and
images. Unchanged color-audit records were reused, with stale records eligible for
reinspection. Full training, validation, and official manifests matched the existing
run. Evidence is saved in `dataset/reviews/2026-10-27/verification_summary.json`.

A separate tiny real-image execution ran every model-stage cell: feature baseline,
preview, three training epochs through both phases, rotating coverage, best-checkpoint
reload, full report, official predictions, and single-image prediction. Temporary
model outputs were discarded. These checks ran on CPU; CUDA/MPS throughput was not
benchmarked. No full new accuracy experiment was completed during this review.

The executed notebook from before editing is preserved at
`dataset/reviews/2026-10-27/testing_2_before_review.ipynb`. New outputs go under
`dataset/processed/student_v6_reviewed_20/<timestamp>/`. The edited notebook's stale
outputs were cleared so they cannot be mistaken for results from the corrected code.
