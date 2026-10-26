# Data and training review — 25 October 2026

The full dataset is now extracted. See the [26 October full-data audit](color_cleaning_report.md)
for the current cleaning and color results. The missing-file findings below describe
the earlier partial-data run.

## What the supplied results show

In the screenshot, training loss falls from 4.6511 to 3.0246, while validation macro
F1 rises from 0.0336 to 0.1169 over six epochs. This indicates learning, but overall
performance across classes remains weak. Accuracy alone does not explain the issue;
the earlier output did not include training accuracy, validation loss, or per-class
prediction counts. The palette/transparency warning is an image-conversion problem,
not evidence by itself that the network has failed.

The local saved run is different from the screenshot. Its image-check reports show:

| Local evidence | Value |
| --- | ---: |
| Readable training candidates in the old scope | 4,000 |
| Missing training candidates in the old scope | 186,456 |
| Readable official held-out images | 0 of 9,806 |
| Classes surviving the old local image/support checks | 3 |
| Training images after the old fast cap | 150 |
| Per-epoch validation images | 30 |

Only the Reddit and Twitter image directories are available locally. The local
results cannot establish what happened in the Colab run. However, the old code also
computed a missing-file count without enforcing `REQUIRE_ALL_IMAGES`; this allowed
incomplete data to reach training. The new per-class coverage guard rejects this
local partial download as intended.

A visual sample also exposed a label conflict worth reviewing: `twitter/554_0.png`
is labeled `psoriasis`, while its image contains the text "Dermatomyositis" and its
caption lists differential diagnoses. This is evidence of ambiguity, not a new
diagnosis assigned by this review. The new caption screen flags explicit uncertain/
differential wording, excluding those candidates while preserving them in a review
report. It flags 9,455 rows across the cached raw CSV, including rows outside the
selected task. The heuristic can also exclude valid examples and does not replace
qualified label review.

## Decisions changed

| Previous choice | Revised choice | Reason |
| --- | --- | --- |
| Hundreds of labels with up to 50 training images each | User-approved 20-label experiment, up to 300 images per class | More examples per decision while keeping epochs small |
| Ten validation images per class | Up to 50, with minimum support checks | Reduce sensitivity to a handful of examples |
| Permit available files to determine surviving classes | Require the explicit 20 labels and 95% image coverage per class | Missing downloads should not silently redefine the task |
| Drop every copy of a duplicate | Keep one same-label copy; reject conflicts and official copies | Preserve useful unique examples |
| File-byte hashes only | File and decoded-pixel hashes | Detect identical pixels with different file metadata |
| Crop images for training/evaluation | Resize with padding | Retain content near image edges |
| Unfreeze the whole network after two epochs | Unfreeze the final block and classifier | Reduce adaptation of pretrained features on a small dataset |
| Judge training from loss/accuracy/F1 alone | Add validation loss, training accuracy, class coverage, errors and timing | Distinguish overfitting, collapse, and throughput issues |

The earlier 50-image cap was a speed-oriented smoke-test setting. It was too
aggressive as a default for assessing useful performance across hundreds of classes.
The new setup does not claim that a higher score on 20 classes proves improvement
on the original task: the tasks are different.

The exact 20 names are visible in the notebook. They retain dataset spelling and
individual labels; no synonyms, parent/child diagnoses, or uncertain annotation
strings are silently merged. A first candidate, `seborrheic dermatitis`, failed
the metadata support criteria and was replaced with `cellulitis` before training.
This choice used support counts, not validation performance.

## What was checked

Using the cached repository CSVs, after label selection and official-group exclusion:

- All 20 requested labels pass the metadata support criteria.
- There are 46,651 candidates before image quality/duplicate checks.
- The group split contains 37,321 training and 9,330 internal validation candidates.
- Sampling selects 6,000 training and 1,000 per-epoch validation rows.

These are **metadata-only counts**. Complete readable-image and pixel-duplicate
checks may reduce them; the notebook rechecks every class before training.

Sixteen regression checks pass. A small generated-image run also completed the color/
texture baseline, head warmup, final-block fine-tuning, checkpoint reload, full
validation report, and official prediction path. It used the cached pretrained
ResNet-18 weights. Synthetic scores are not presented as dermatology performance.
The incomplete-local-data rejection was tested against the actual local file paths.

## Feature engineering and remaining decisions

A 12-feature color/texture classifier now gives a simple comparison on the same rows
as the CNN. It measures whether global RGB/brightness/saturation/edge statistics are
useful in this dataset. It does not prove that those features should be added to the
network. ResNet already learns visual features. Evaluate the baseline before adding
another branch or replacing the backbone with a larger model.

The remaining work for evidence of improved accuracy is a fresh Colab run with the
complete archives. Inspect the training montage and error report for incorrect labels,
non-photo content, and source artifacts. Grouping is a leakage precaution, not a
patient-independent guarantee. The official CSV lacks diagnosis targets, so a truly
independent scored evaluation needs a suitable labeled evaluation set.

## References used for the implementation

- [Derm1M dataset card](https://huggingface.co/datasets/redlessone/Derm1M)
- [PyTorch transfer learning](https://docs.pytorch.org/tutorials/beginner/transfer_learning_tutorial.html)
- [PyTorch automatic mixed precision](https://docs.pytorch.org/docs/stable/amp.html)
- [Pillow image operations](https://pillow.readthedocs.io/en/stable/reference/ImageOps.html)
- [Scikit-learn logistic regression](https://scikit-learn.org/stable/modules/generated/sklearn.linear_model.LogisticRegression.html)
