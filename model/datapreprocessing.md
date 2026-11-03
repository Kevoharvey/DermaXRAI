# Simple Derm1M notebook

Open [`testing_2.ipynb`](testing_2.ipynb) and run from top to bottom. All processing,
training, and prediction code is inside that notebook. The code uses direct
filenames, basic pandas operations, scikit-learn, and PyTorch for ResNet18.

The notebook expects these CSV files beside it:

- `Derm1M_v2_pretrain.csv`
- `Derm1M_v2_validation.csv`
- `image_color_audit.csv` from the completed image audit

The extracted image folders also belong beside the notebook. For this local copy,
the CSVs and image folders are already available there through local links to the
existing data. Images have not been copied or changed. On Colab, arrange the inputs
in the same way. No Hugging Face login or download runs inside the notebook.

The steps are: read the CSVs, clean labels, group related pictures, check images,
select the 20 diagnoses, split by group, prepare images, train, and evaluate.
There are no JSON reports, environment variables, automatic project-folder searches,
or external Python helpers used by the notebook.

## Main settings

```python
run_name = "simple_run_1"
fast_mode = False
images_per_class = 300
reuse_image_checks = True
batch_size = 32
number_of_epochs = 20
dropout_rate = 0.25
weight_decay = 0.001
```

Full mode uses the 36,777 training images for the selected 20 diagnoses: 1,150 batches
at batch size 32. Fast mode cycles through up to 300 images per class each epoch,
which is at most 6,000 images / 188 batches. Its validation sample stays fixed.
Small classes repeat sooner, and early stopping may end before all images are used.
The final report evaluates the full 9,195-image internal validation split.

Use a different `run_name` for each experiment. Results save beside the notebook
as ordinary CSV files and a PyTorch `.pth` checkpoint. A checkpoint with the same
name prevents accidental retraining over an existing model. Restart the kernel and
run every cell for a fresh experiment.

## Reusing the image audit

`reuse_image_checks=True` reads the existing audit by filename and avoids decoding
all images again. It assumes the image files have not changed since the audit.
If images were edited/replaced, or the audit is missing, set the value to False and
run from the beginning. The notebook then checks images itself and writes a separate
`<run_name>_image_checks.csv`; it leaves the original full audit intact.

Color conversion is still used every time a model input is loaded. The function
corrects EXIF orientation, handles transparency, and converts valid embedded color
profiles to sRGB. Untagged RGB images are assumed to be sRGB. Unsupported color
cases are flagged instead of silently rescaled.

## What the scores mean

Training uses augmentation, dropout, and class-weighted loss. Validation uses
unmodified evaluation transforms and unweighted loss. The final full validation
report includes the sample used for checkpoint selection, so it is not an independent
final test. The official CSV has no diagnosis labels and only supplies predictions.

File integrity and duplicate checks do not prove medical label accuracy. Source/case
groups cannot establish patient independence, and exact pixel hashes do not detect
all cropped or compressed copies. The earlier cleaning and review reports document
these limits.

## Verification

The simplified notebook reproduces the previous full training/validation image,
label, group, and pixel-hash rows. Tests check color conversion, label handling,
image checks, training rotation, scheduling, checkpoint preservation, and prediction
row order. The notebook does not import the test file.

```bash
python3 -m unittest discover -s model -p 'test_notebook_review.py' -q
```
