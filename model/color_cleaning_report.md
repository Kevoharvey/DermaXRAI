# Full dataset cleaning and color audit — 26 October 2026

## Completed work

Inspected **423,016 extracted image files** across all nine source directories.
The training and official manifests account for every audited file: no missing
manifest files and no extra inventory files were found. Every file decoded and
passed the current color-policy checks. This is a file-integrity/color result,
not confirmation that every diagnosis label is correct.

## Original pixel modes

| Mode | Images | Handling when loaded |
| --- | ---: | --- |
| RGB | 422,203 | Apply embedded ICC-to-sRGB conversion when available |
| RGBA | 675 | Convert color, then composite alpha onto white |
| L (grayscale) | 85 | Replicate grayscale into three RGB channels |
| P (indexed palette) | 53 | Expand palette; preserve transparency before compositing |
| Total | 423,016 | Three-channel RGB input |

There were **679 images with alpha/transparency metadata** and **2,741 images with
embedded ICC profiles**. Profiles include 1,053 named Display P3 and 28 named Adobe
RGB/Adobe RGB (1998), as well as sRGB and monitor-specific profiles. All valid
embedded profiles are transformed to sRGB with Pillow ImageCms. RGB pixel mode
alone does not establish that a file is sRGB.

The other 420,275 images have no embedded ICC profile. Untagged RGB/grayscale web
images are treated as assumed sRGB, and that assumption is recorded per image.
An absent profile does not let us reconstruct the original camera/display color
space. EXIF color-space tags and PNG gamma are also recorded; an uncalibrated EXIF
tag is not treated as proof of Adobe RGB.

No corrupt files, invalid profiles, high-bit-depth cases, or multi-frame cases
were found by this scan. Tests still cover how those cases are held for review.
Across the entire inventory, 1,855 images are smaller than 64 pixels on at least
one side; 305 have low contrast under the review threshold. These counts include
unlabeled/out-of-scope and official images. Low contrast is flagged rather than
used as an automatic skin-tone/brightness filter.

## Label and image cleaning

The full 413,210-row training manifest was processed before selecting the 20 classes:

| Stage | Images/rows |
| --- | ---: |
| Original training rows | 413,210 |
| Excluded unknown, multi-diagnosis, or conflicting filename targets | 143,923 |
| Additional single-label images excluded by uncertain-caption screen | 4,834 |
| Remaining single-label candidates | 264,453 |
| Excluded because their source/case group also appears in the official set | 77,788 |
| Candidates checked for image quality/duplicate content | 186,665 |
| Excluded identical-pixel images with contradictory labels | 1,142 |
| Excluded repeated same-label copies, retaining one representative | 923 |
| Excluded images smaller than 64 pixels on the shorter side | 764 |
| Excluded training copies of readable official images | 171 |
| Full cleaned single-label candidate pool | **183,665** |

The image-removal reasons above are mutually exclusive. The uncertain-caption
heuristic flags 9,455 raw rows overall; some were already excluded by label rules,
so it removes 4,834 additional single-label candidates. Flagged captions are saved
for review and are never used to guess replacement diagnoses.

The broad cleaned pool retains 14,358 raw normalized label strings, many outside
our reviewed task. It is a structurally cleaned candidate pool, not a medically
curated 14,358-class dataset. **Training remains limited to the selected 20 labels.**
Duplicate conflicts are checked across the broad pool before that restriction.

## Prepared 20-class experiment

All 20 chosen labels passed the verified image/group/source requirements. There
are **45,972 images** in the supported 20-class pool after cleaning. The group split
and fast sample contain:

| Purpose | Images |
| --- | ---: |
| Full training partition | 36,777 |
| Training sample per epoch | **6,000** (300 per class) |
| Validation sample per epoch | **1,000** (50 per class) |
| Full internal validation partition | **9,195** |
| Official held-out inputs | 9,806 |

Verified that training and full internal validation share no source/case groups
or canonical-pixel hashes. The full cleaned pool has unique canonical pixel
hashes and none match the readable official images. Source/case grouping remains
a proxy; patient independence cannot be guaranteed without patient identifiers.
Official rows and their order are unchanged. The official CSV has no diagnosis
labels, so it still cannot supply measured test accuracy.

## Saved outputs

- Full per-image mode/profile/color/integrity report:
  [`image_color_audit.csv`](../dataset/processed/color_audit_v1/image_color_audit.csv)
- Machine-readable color summary:
  [`summary.json`](../dataset/processed/color_audit_v1/summary.json)
- Full cleaned pool:
  [`cleaned_single_label_manifest.csv`](../dataset/processed/student_v4_color_20/cleaned_single_label_manifest.csv)
- Excluded images and their reasons:
  [`removed_training_images.csv`](../dataset/processed/student_v4_color_20/removed_training_images.csv)
- Per-class support and selected lists:
  [`split_support.csv`](../dataset/processed/student_v4_color_20/split_support.csv),
  [`train.csv`](../dataset/processed/student_v4_color_20/train.csv),
  [`val.csv`](../dataset/processed/student_v4_color_20/val.csv),
  [`val_full.csv`](../dataset/processed/student_v4_color_20/val_full.csv)
- Notebook using the shared color-correct loader:
  [`testing_2.ipynb`](testing_2.ipynb)

Original image files are preserved. Color standardization happens when loading
images; we do not create another lossy copy of every image. Cached audit records
are reused only when the policy version, file size, and modification time match.
The new training output folder is `student_v4_color_20/`, preserving earlier runs.
`DOWNLOAD_IMAGES` now defaults to `False`.

## Verification

All **24 regression checks passed**, covering ICC conversion, transparency, nested
EXIF tags, invalid profiles/high-bit-depth review, cache invalidation, labels,
duplicates, groups, and prediction row preservation. The actual prepared files also
passed checks for balanced sample counts, unique cleaned pixels, minimum dimensions,
absence of training/validation/official pixel overlap, and unchanged official rows.
No model training was run for this cleaning task.

References: [Pillow ImageCms](https://pillow.readthedocs.io/en/stable/reference/ImageCms.html)
and [Pillow image modes](https://pillow.readthedocs.io/en/stable/handbook/concepts.html).
