# Dataset coverage and next training experiment — 26 October 2026

## What “one million” means

The [Derm1M dataset card](https://huggingface.co/datasets/redlessone/Derm1M)
reports 1,029,761 image–text pairs from 403,563 unique images. Pairs and independent
photographs are different units. The downloaded v2 manifests actually contain
413,210 training rows and 9,806 official held-out rows, matching the 423,016 files
in the completed local audit. The card and local release counts differ; the local
counts below come from the actual files, not an assumed million-image inventory.

The existing audit inspected every extracted image. This task reran preparation,
reusing audit entries only where file size, modification time, and color-policy
version still match. No manifest images were missing from the audited inventory.

## How much data can this classifier use?

| Stage | Images |
| --- | ---: |
| Downloaded training manifest | 413,210 |
| Clear single-label candidates after caption screening | 264,453 |
| Candidates after separating groups related to official inputs | 186,665 |
| Structurally cleaned single-label pool | 183,665 |
| Cleaned images matching the agreed 20 diagnoses | 45,972 |
| Training partition | 36,777 |
| Internal validation partition | 9,195 |

The other images are not all suitable additional examples for these same 20 outputs.
They include other diagnoses, ambiguous/multiple targets, repeated content, and
groups reserved to prevent leakage. Broadening the diagnosis list or merging label
names would change the task and needs a reviewed label mapping. Existing cleaning
rules and all source files remain intact.

## Applied changes

The notebook now defaults to a separate `student_v5_rotating_20` experiment:

- Fast mode uses up to 300 images per diagnosis each epoch, cycling through a
  reproducible queue instead of repeating the same fixed subset.
- Each class's queue starts with one image per group, then second images, and so
  on. It wraps when exhausted; no image occurs twice within an epoch. Smaller
  classes repeat across epochs sooner. This does not guarantee independent images
  within a batch, and near-duplicate video frames may still remain.
- Validation stays fixed. The class list, split seed, image transforms, ResNet-18,
  batch size 32, learning rates, dropout, and weight decay are unchanged.
- `train_full.csv` records the entire training partition. `train.csv` records the
  first sample, `epoch_manifests/` records each selected epoch, and
  `training_coverage.csv` reports exposure from completed training epochs.
- `validation_by_source_and_class.csv` adds diagnosis-specific recall and support
  to source-level diagnostics. Source accuracy alone can be misleading when class
  distributions differ.
- The previously measured feature baseline and official predictions default to off
  while experimenting. The optional feature baseline uses first-epoch rows; the
  rotating CNN has greater cumulative training exposure.

`FAST_MODE=False` still trains on the whole training partition each epoch.
`ROTATE_TRAINING_IMAGES=False` restores fixed-subset fast training. Use a different
output folder for each experiment. Early stopping remains enabled.

## Verified coverage, not measured accuracy

The actual training manifests were simulated through the sampling code:

| Completed epochs, if reached | Distinct training images | Pool coverage |
| --- | ---: | ---: |
| 1 | 6,000 | 16.3% |
| 5 | 21,722 | 59.1% |
| 10 | 28,586 | 77.7% |
| 15 | 33,123 | 90.1% |
| 20 | 35,116 | 95.5% |
| 26 | 36,777 | 100% |

These are planned exposure counts, not completed full training epochs or a reason
to ignore early stopping. The default epoch ceiling remains 20. Sampling the same
number of images keeps training work similar to the earlier fast experiment, but
actual runtime must be measured on the execution device.

The saved v4 folder now contains a newer full-data run: at inspection it had reached
epoch 11 with 0.5052 full-validation accuracy and 0.4200 macro F1. Its best macro F1
through that point was 0.4207 at epoch 9. The earlier pasted fast-run final report
had 0.3570 full-validation accuracy and 0.3189 macro F1. Broader data exposure is
encouraging, but this is not a controlled proof of the cause: full mode also changes
class proportions, loss weights, optimizer steps per epoch, and checkpoint selection.
The existing run was not interrupted or overwritten by this task.

## Content quality findings

A 16-image training montage spans four diagnoses and four source types. It shows
different framing, resolution, backgrounds, watermarks, and dermoscopic versus
ordinary photographs. This small review does not estimate dataset-wide error rates.

One concrete mismatch was verified:
`youtube/zuOTr65549g_frame_559_0_0.jpg` visibly shows feet/ankles, while its caption
describes a face and left cheek. The metadata label is acne. This demonstrates an
image–caption alignment problem; it does not establish a replacement diagnosis.
It is flagged for review rather than relabeled automatically. Captions remain
excluded from model inputs.

The training review queue samples up to three distinct groups per diagnosis/source,
with the verified mismatch added explicitly. Review the queue before making medical
label corrections or excluding whole sources. The largest psoriasis training group
contains 314 images, so image count is also not equivalent to independent case count.

## Artifacts

All new prepared outputs are under `dataset/processed/student_v5_rotating_20/`:

- `planned_training_coverage.csv`: sampling simulation for 26 epochs.
- `training_pool_support.csv`: image/group counts and coverage horizon per class.
- `training_pool_by_source_and_class.csv`: training source composition by diagnosis.
- `training_label_review.csv`: stratified review queue with blank annotation fields.
- `training_content_review.png` and `.csv`: inspected montage and exact filenames.
- `verified_content_issues.csv`: documented visual/caption mismatch.
- `previous_validation_by_source_and_class.csv`: diagnostic breakdown of the final
  validation predictions available during this check; not a result for the new model.

## Validation and how to run

All 25 regression tests passed. Actual-data checks confirmed unchanged full
validation and official manifests, the same deterministic first-epoch fast samples,
and no training/validation group or pixel-hash overlap across all 26 sampled epochs.
A small three-epoch test on real images exercised rotation, warmup, fine-tuning,
coverage logs, best-checkpoint reload, and source/class reporting. Its temporary
outputs were discarded; it is a code check, not an accuracy estimate.

Open `testing_2.ipynb`, restart the kernel, and run from the first cell for the new
experiment. No additional archive download is required. If an existing full-data
run is still active, allow it to finish and save its results before restarting.
The new rotating model has not yet completed a full training run, so no improvement
in its accuracy is claimed.
