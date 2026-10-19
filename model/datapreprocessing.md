# Derm1M Data Split and Class Balancing

Issue #10 · DermaXRAI · 13 October 2026

## Summary

53,697 of 413,210 Derm1M images were split 70 / 15 / 15 into train, validation and test sets with no leakage between them, and class imbalance is handled with a class-weighted loss.

| Item | Value |
| --- | --- |
| Source | Derm1M_v2_pretrain.csv, 413,210 images |
| Selected | 53,697 images in 6 classes |
| Split sizes | train 37,586 · val 8,054 · test 8,057 |
| Leakage | 0 shared groups and 0 shared files between any two splits |
| Imbalance (train) | 5.33 : 1 (psoriasis 10,248 vs acne 1,922) |
| Balancing strategy | Class-weighted cross-entropy, weights computed on train only |

Script: `model/split_balance.py` · Classes: `model/derm1m_classes.json` · Results: `dataset/splits/SPLIT_REPORT.md`

## 1. Class selection

The six classes are the team's provisional list, and 53,697 images match them cleanly: acne, eczema, psoriasis, melanoma, benign_nevus, fungal_infection. To change them, edit `model/derm1m_classes.json` and re-run the script; key order in that file is the class index order.

Derm1M `disease_label` is a comma-separated list of terms, for example `atopic dermatitis, eczema, dermatitis`. An image is kept only if **every term maps to the same class**. The generic terms `dermatitis` and `nail disease` are ignored. Terms are matched exactly, not by substring, so `mycosis fungoides` (a lymphoma) never matches fungal_infection.

| Step | Images |
| --- | --- |
| Rows in manifest | 413,210 |
| Duplicate records removed | 0 |
| Images with conflicting labels removed | 0 |
| Excluded: a term falls outside the selected classes (includes 102,380 no definitive diagnosis) | 359,030 |
| Excluded: terms span several classes (for example `eczema, psoriasis`) | 483 |
| Classes dropped for having fewer than 500 images | none |
| **Selected images** | **53,697** |

`label_mapping.csv` lists every raw label used per class with its count. The official `Derm1M_v2_validation.csv` has no `disease_label` column, so it cannot be used for supervised splitting.

## 2. Leakage control

Derm1M has no patient IDs, so images that likely show the same patient or lesion are grouped by identifiers in the file path, and a group never crosses splits.

| Source | Group key | Example |
| --- | --- | --- |
| YouTube | Video ID | `youtube/ykOs8g7zjDc` |
| IIYI, Twitter, Reddit | Case or post ID | `IIYI/26549` |
| PubMed | Article (PMC ID) | `PMC4886598` |
| Textbooks (`edu/`, `note/`) | Book and page | `<book title>_00178` |
| `public`, `public_medical_dataset` | None available | each image is its own group |

Checks performed:

- **Duplicate records and conflicting labels:** none found in v2.
- **Identical image content:** with `--image-root`, byte-identical images (SHA-256) are merged into one group. This check needs the extracted images and has not been run on the real data.
- **After splitting:** the script counts shared group IDs, filenames and content hashes for every pair of splits and stops if any count is not 0.
- **Group size:** the largest single group holds at most 2.6% of its class, so no group dominates a split.

## 3. Splitting method

`StratifiedGroupKFold` with 20 shuffled folds (seed 42) gives exact 70 / 15 / 15 ratios, and class shares match across splits within 0.03 percentage points. Each 5% fold keeps the class proportions and no group appears in two folds. Folds 0–2 form the test set, folds 3–5 the validation set, and the remaining 14 the training set.

| Class | Train | Val | Test | Share | Groups |
| --- | --- | --- | --- | --- | --- |
| acne | 1,922 | 411 | 412 | 5.1% | 1,197 |
| eczema | 7,271 | 1,557 | 1,559 | 19.3% | 6,024 |
| psoriasis | 10,248 | 2,198 | 2,198 | 27.3% | 3,865 |
| melanoma | 5,670 | 1,215 | 1,215 | 15.1% | 3,521 |
| benign_nevus | 7,532 | 1,614 | 1,614 | 20.0% | 7,112 |
| fungal_infection | 4,943 | 1,059 | 1,059 | 13.2% | 4,173 |
| **Total** | **37,586** | **8,054** | **8,057** | 100% | 24,590 |

The group counts per class add up to more than 24,590 because 952 groups, for example a video showing several conditions, contain images of more than one class.

## 4. Class imbalance

A class-weighted loss is the selected strategy for the moderate 5.33 : 1 imbalance between psoriasis (10,248 training images) and acne (1,922).

| Strategy | Effect on this data | Decision |
| --- | --- | --- |
| Class-weighted loss | Raises the loss for rare classes (acne weight 3.26, psoriasis 0.61) without changing the data | **Selected** |
| Weighted sampling (`WeightedRandomSampler`) | Balanced batches, but each acne image repeats about 3× per epoch and about half of the psoriasis images go unseen in each epoch | Fallback if minority recall stays low |
| Train-only oversampling / augmentation | Adds minority variety at the cost of an extra pipeline | Optional, only after a baseline |
| Undersampling | Would discard 69% of the training data | Rejected |

Why weighted loss: it handles a 5:1 imbalance without duplicating or discarding images, it keeps validation and test at their natural distribution so metrics stay realistic, and it is a one-line change.

```python
import json, torch

classes = list(json.load(open("model/derm1m_classes.json"))["classes"])
weights = json.load(open("dataset/splits/class_weights_train.json"))
criterion = torch.nn.CrossEntropyLoss(weight=torch.tensor([weights[c] for c in classes]))

# Fallback: balanced sampling
# sampler = torch.utils.data.WeightedRandomSampler([weights[l] for l in train_df.label], len(train_df))
```

Report macro F1 and per-class recall, not only accuracy, because accuracy hides errors on acne.

## 5. Test-set isolation

The test set is used once, for the final evaluation, and nothing derived from it reaches training.

- `test.csv` is never used for model selection, early stopping or hyperparameter tuning; those use `val.csv` only.
- Class weights and any other statistic (normalisation, sampling) are computed from the training split only.
- Oversampling or augmentation is never applied to validation or test data.

## 6. Reproducibility

The same manifest and seed always produce byte-identical split files, which was verified by re-running the script on the real data. `split_config.json` stores the seed, ratios, method, group pattern, the manifest's SHA-256, library versions, split sizes and leakage counts.

```bash
python model/split_balance.py --manifest path/to/Derm1M_v2_pretrain.csv
```

If `model/testing.ipynb` has been run, the CSV is already in the Hugging Face cache:

```python
from huggingface_hub import hf_hub_download
print(hf_hub_download("redlessone/Derm1M", "Derm1M_v2_pretrain.csv", repo_type="dataset"))
```

| Option | Default | Purpose |
| --- | --- | --- |
| `--classes` | `model/derm1m_classes.json` | Selected classes and label mapping |
| `--image-root` | none | Extracted image folder; enables the duplicate-content check |
| `--ratios` | `0.70 0.15 0.15` | Train / val / test ratios (multiples of 0.05) |
| `--min-count` | `500` | Minimum images per class |
| `--seed` | `42` | Random seed |
| `--out-dir` | `dataset/splits` | Output folder |

## 7. Outputs

The script writes eight files to `dataset/splits/`; `filename` in them is relative to the folder where the Derm1M image archives are extracted.

| File | Content |
| --- | --- |
| `train.csv`, `val.csv`, `test.csv` | filename, label, source, group_id per image |
| `class_distribution.csv` | Images, share and groups per class and split |
| `class_weights_train.json` | Loss weights computed on the training split |
| `label_mapping.csv` | Raw Derm1M labels assigned to each class, with counts |
| `split_config.json` | Parameters, library versions, input hash, sizes, leakage counts |
| `SPLIT_REPORT.md` | Generated summary of all of the above |

## 8. Limitations

- **Ungrouped images.** 24% of the selected images (the public sources) have no group key: 49% of benign_nevus, 31% of eczema, 28% of melanoma and 27% of fungal_infection. Different images of the same patient there can land in different splits. `--image-root` removes exact copies; near-duplicates would need perceptual hashing.
- **Source bias.** 38% of benign_nevus and 23% of melanoma images are ISIC dermoscopy images, while 73% of psoriasis images are YouTube frames. A model can learn the image source instead of the disease, so evaluate per source using the `source` column.
- **Label noise.** Labels come from Derm1M captions. The mapping is conservative, but no dermatologist has reviewed it.
- **Page-level grouping.** Textbook groups do not catch the same patient shown on different pages.
- **Provisional classes.** The class list is the team's temporary one and must be confirmed before final training.

Data: Derm1M (Yan et al., 2025, [arXiv:2503.14911](https://arxiv.org/abs/2503.14911)), licensed CC BY-NC 4.0 for non-commercial research use only.