# Previous run baseline: simple_run_1

These are the previous run's final evaluation results, supplied by the user. Keep this snapshot as the reference for comparing later experiments.

## Best checkpoint

| Metric | Value |
|---|---:|
| Best epoch | 8 |
| Full validation accuracy | 0.5040783034257749 |
| Full validation macro F1 | 0.4152026463581972 |
| Full validation loss | 1.8028489103000924 |
| Validation images | 9195 |

These are internal validation results. The validation set was also used to select the best checkpoint; this is not an independent test-set evaluation.

## Per-class results

Values below retain the six decimal places printed in the supplied report. Support is the number of validation images with that class label.

| Class | Precision | Recall | F1 | Support |
|---|---:|---:|---:|---:|
| acne | 0.410127 | 0.507837 | 0.453782 | 319 |
| basal cell carcinoma | 0.540117 | 0.550898 | 0.545455 | 501 |
| bullous pemphigoid | 0.238462 | 0.236641 | 0.237548 | 131 |
| cellulitis | 0.268657 | 0.153846 | 0.195652 | 117 |
| eczema | 0.525223 | 0.527309 | 0.526264 | 1007 |
| folliculitis | 0.167883 | 0.258427 | 0.203540 | 178 |
| herpes zoster | 0.477352 | 0.480702 | 0.479021 | 285 |
| impetigo | 0.290541 | 0.298611 | 0.294521 | 144 |
| lichen planus | 0.338164 | 0.262172 | 0.295359 | 267 |
| melanoma | 0.562923 | 0.474344 | 0.514851 | 877 |
| molluscum contagiosum | 0.604167 | 0.568627 | 0.585859 | 357 |
| nevus | 0.659574 | 0.695513 | 0.677067 | 1248 |
| pityriasis rosea | 0.300000 | 0.403846 | 0.344262 | 104 |
| psoriasis | 0.514646 | 0.531837 | 0.523101 | 1916 |
| rosacea | 0.314286 | 0.198198 | 0.243094 | 111 |
| scabies | 0.239437 | 0.208589 | 0.222951 | 163 |
| seborrheic keratoses | 0.794038 | 0.830028 | 0.811634 | 353 |
| squamous cell carcinoma | 0.336870 | 0.333333 | 0.335092 | 381 |
| urticaria | 0.421712 | 0.441048 | 0.431163 | 458 |
| vitiligo | 0.437788 | 0.341727 | 0.383838 | 278 |
| macro avg | 0.422098 | 0.415177 | 0.415203 | 9195 |
| weighted avg | 0.504431 | 0.504078 | 0.502600 | 9195 |

## Results by image source

Mean is the fraction of predictions that were correct within that source. These are raw source accuracies; the disease mix can differ between sources.

| Source | Count | Mean accuracy |
|---|---:|---:|
| IIYI_chinese | 1505 | 0.458472 |
| public | 3002 | 0.692205 |
| public_medical_dataset | 72 | 0.694444 |
| pubmed_english | 540 | 0.290741 |
| pubmed_fail | 260 | 0.361538 |
| reddit_english | 31 | 0.387097 |
| textbook_chinese | 88 | 0.363636 |
| textbook_english | 682 | 0.398827 |
| textbook_fail | 313 | 0.399361 |
| twitter_english | 67 | 0.432836 |
| youtube | 2635 | 0.415939 |
