# Derm1M: a focused 20-class experiment

Open [`testing_2.ipynb`](testing_2.ipynb), select a GPU in Colab, and run from the
first cell. The code is self-contained, with an explanation before every code cell.
See [`training_improvement_report.md`](training_improvement_report.md) for the current
coverage experiment and [`data_review.md`](data_review.md) for the findings and decisions behind this revision.
The completed full-data results are in [`color_cleaning_report.md`](color_cleaning_report.md).

## Run the notebook

Install `requirements.txt` in the active Python environment. Authenticate with
Hugging Face and accept the Derm1M repository terms if required. Set these optional
paths when using local files:

```bash
export DERM1M_MANIFEST=/path/to/Derm1M_v2_pretrain.csv
export DERM1M_TEST_MANIFEST=/path/to/Derm1M_v2_validation.csv
export DERM1M_IMAGE_ROOT=/path/to/extracted/images
```

Without local CSV paths, metadata is read from the Hugging Face cache or downloaded.
`DOWNLOAD_IMAGES=True` downloads and extracts the large image archives, including
`validation_data.zip`. It now defaults to `False` because the local dataset is extracted. Image checks
still run once before training; the per-epoch caps do not reduce archive size.

Default settings:

```python
FAST_MODE = True
ROTATE_TRAINING_IMAGES = True
MAX_TRAIN_IMAGES_PER_CLASS = 300
MAX_VAL_IMAGES_PER_CLASS = 50
RUN_FEATURE_BASELINE = False
EPOCHS = 20
WARMUP_EPOCHS = 2
```

This is at most **6,000 training images and 1,000 validation images per epoch** for
20 classes. Fast mode now cycles through a fixed queue per class, so each epoch
uses different training images until that class's queue wraps. Validation stays
fixed. `ROTATE_TRAINING_IMAGES=False` restores the original fixed training sample.
`train_full.csv` preserves every eligible training row; `epoch_manifests/` records
each selected sample and `training_coverage.csv` counts distinct images from completed
epochs. Early stopping can end before every image is used.

Set `FAST_MODE=False` to use all supported images for the same 20-class
task. Use a new output folder when comparing experiments. Restart and rerun all cells
when changing the class list, dataset limits, or preprocessing; do not reuse an old
model or partially executed notebook. Epoch duration is printed, not estimated.

`METADATA_ONLY=True` audits tables without training or opening images. It does not
establish how many images will pass the full checks.

## Local macOS download error: too many open files

For `zmq.error.ZMQError: Too many open files`, stop the failed run and restart the
notebook kernel. Run the new **0. Prepare the local notebook** cell before importing
Hugging Face or logging in. It raises a low process file limit to 4,096 where the
existing hard limit allows it, prints the actual limit, and disables Xet on macOS.
Colab keeps its default download backend. Xet is a possible contributor to file/
socket pressure; the exception alone does not identify which library opened the handles.

The environment variable must be set before Hugging Face is imported. Rerunning the
setup after an earlier Xet-enabled import gives a clear restart instruction.
Keep the same image root: completed extraction markers skip Reddit, Twitter, and
other finished archives. Only the interrupted archive may need another download.
The progress bars do not establish that an archive is complete; the downloader now
prints when it returns and when extraction begins. No global system limit or
package installation is changed.

## Data selection and cleaning

### Audit every extracted image, then prepare the clean lists

From the project folder, run:

```bash
python3 model/audit_dataset.py --image-root ~/Derm1M_images --workers 4
python3 model/prepare_dataset.py --image-root ~/Derm1M_images
```

The first command audits **all nine extracted directories**, not only the selected
classes. It records mode, image format, transparency, frame count, embedded ICC
profile name/space, EXIF color-space tag, PNG gamma, dimensions, contrast, and file/
canonical-pixel hashes. Four workers bound the number of concurrently open files.
A SQLite checkpoint resumes unchanged files; changing the color policy invalidates
old records. The CSV and summary are in `dataset/processed/color_audit_v1/`.

The second command uses only locally cached/provided metadata; it does not download
archives or train a model. It checks label and duplicate conflicts across the full
single-label pool before choosing the 20-class experiment. It writes the full cleaned
pool, removed-row reports, and training/validation lists to
`dataset/processed/student_v5_rotating_20/`. The notebook reuses up-to-date audit records
instead of decoding every unchanged image again.

Source images remain intact. Cleaning creates filtered manifests, and the shared
loader standardizes color when images are used. There is no lossy rewrite of the
entire dataset. The audit covers file integrity/color metadata, not clinical label
correctness or proof that every image is a suitable phone photograph.

The settings cell explicitly lists 20 individual labels. Synonyms and broader labels
are not automatically merged. Multi-diagnosis rows, unknown labels, and contradictory
targets for the same filename are rejected. `label_scope.csv` records counts for the
labels outside this experiment as well as those included.

`EXCLUDE_UNCERTAIN_CAPTIONS=True` also flags explicit differential/uncertain diagnosis
wording and saves those rows to `uncertain_caption_rows.csv`. These rows are excluded
pending review. This conservative text heuristic can exclude valid examples; it never
guesses a new target from a caption.

Training candidates related to the official held-out set are excluded. Source/case,
video, paper, and textbook-page groups remain together when creating the internal
80/20 split. These proxies cannot guarantee patient independence without patient IDs.

For every selected class, at least 95% of candidate files must be readable. Coverage
is reported by class and source. `REQUIRE_ALL_IMAGES=True` raises that requirement
to every candidate image. A partial download must not silently redefine the task.

Image cleaning:

- Correct EXIF orientation and composite transparent/palette images onto white.
- Remove unreadable images and images under 64 pixels on the shorter side.
- Check both file hashes and decoded RGB pixel hashes.
- Remove all identical-pixel examples with contradictory labels.
- Keep one representative of a repeated image with a consistent label.
- Exclude training copies of readable official images.
- Flag low contrast for review without automatically filtering by brightness or skin tone.

Pixel hashing does not detect all recompressed or cropped duplicates. Missing official
images leave the pixel-based cross-set check incomplete, which the notebook reports.

Before splitting, each selected class must have at least 150 images, 50 groups,
and two sources. After splitting/sampling it must have at least 80 training and
25 validation images. These are experiment guardrails, not proof of valid labels.
Sampling favors distinct training groups before taking more pictures of the same group.

## Preprocessing, features, and model

**Pixel mode is different from color space.** An RGB file can use Display P3,
Adobe RGB, a monitor profile, or sRGB. Valid embedded profiles are transformed to
sRGB using Pillow's color-management support. Transparency is composited onto white
after the profile conversion. Grayscale/palette images become three-channel RGB.

Untagged RGB/grayscale images are recorded as **assumed sRGB**; their true original
color space cannot be recovered from absent metadata. Invalid/mismatched profiles,
unprofiled CMYK/LAB, high-bit-depth images, unprofiled Adobe RGB EXIF tags, and
multi-frame images are held for review instead of being silently reinterpreted.
No histogram equalization, whitening, or skin-tone-dependent filtering is applied.

Images are resized with padding to preserve their full contents and aspect ratio.
Use ImageNet normalization, mild training-only flips/rotations/brightness/contrast,
and deterministic evaluation preprocessing. The checkpoint records the preprocessing
version; prediction also understands the previous notebook's ResNet preprocessing.

The optional feature baseline extracts 12 pixel-based color/texture statistics and
fits a standardized logistic-regression classifier. Its scaler learns from training
only. It uses the first-epoch training rows and the same validation sample. With rotation,
the CNN sees additional training images in later epochs. These are
global image descriptors, including background, not lesion segmentation features.
They are evaluated as a baseline rather than assumed to improve the CNN automatically.

The main network is pretrained ResNet-18 with dropout and a simple linear classifier.
Train only the classifier during warmup, then the final ResNet block (`layer4`) and
classifier. Early blocks and batch-normalization running statistics remain fixed.
Class weights come from selected training rows; weighted sampling is not also applied.
CUDA uses mixed precision and two data-loader workers. CPU/MPS use full precision.
Set `NUM_WORKERS=0` if notebook worker creation fails.

Captions, disease descriptions, source names, symptoms, and hierarchy strings are
not model input features: they can expose the target and are absent for a new photo.

## Evaluation and outputs

Outputs now go to `dataset/processed/student_v5_rotating_20/`; earlier runs are preserved.

- Coverage, removed-image reports, class support, source counts, and a training montage.
- `cleaned_single_label_manifest.csv` for the full cleaned pool and
  `all_class_support.csv` for its label support (from `prepare_dataset.py`).
- `train.csv` (first epoch), `train_full.csv`, `val.csv`, `val_full.csv`, and the unchanged `official_test.csv`.
- Per-epoch selected manifests and cumulative training coverage by class.
- Feature-baseline metrics and the engineered training features.
- Epoch history with training accuracy, weighted training loss, unweighted validation
  loss, accuracy, macro F1, predicted-class count, and elapsed seconds.
- Best checkpoint selected by fixed-sample validation macro F1.
- Final full internal validation report, predictions, errors, source accuracy,
  confusion matrix, and learning curves.

Per-epoch accuracy uses a capped validation sample and is not directly comparable
with full-distribution accuracy. The final full internal validation set includes
those sample rows, so it is not an independent test. Source-level accuracy also
reflects each source's class mix. `validation_by_source_and_class.csv` reports
recall and support for each diagnosis/source combination to help investigate this.

The cached `Derm1M_v2_validation.csv` contains 9,806 image/caption rows with no diagnosis
labels. `RUN_OFFICIAL_TEST=True` exports one prediction/status per original row,
including missing-image rows, without inventing test accuracy. Set `PREDICT_IMAGE`
in the last cell to predict one photo. Predictions cover only the selected labels.

## Verification

```bash
python3 -m unittest discover -s model -p 'test_notebook.py' -v
```

The regression suite covers label handling, transparency, image fingerprints,
coverage, duplicate rules, group separation, sampling, preprocessing, engineered
features, trainable layers, and official prediction ordering. A separate small
end-to-end check exercised feature fitting, both training phases, checkpoint reload,
full validation reporting, and official prediction using generated test images.
Neither check establishes real-world model accuracy or Colab GPU throughput.

Color-specific checks cover embedded sRGB conversion, palette transparency with
profiles, nested EXIF tags, invalid/mismatched profiles, high-bit-depth review,
and resuming/rechecking the full audit after a file changes.
