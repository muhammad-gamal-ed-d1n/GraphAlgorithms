import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

df = pd.read_csv('mst_construction.csv')

distributions = df['Distribution'].unique()

fig, axes = plt.subplots(1, 3, figsize=(15, 5))

fig.suptitle('Prim\'s vs Kruskal Comparison', fontsize=16)

for idx, dist in enumerate(distributions):
    data = df[df['Distribution'] == dist]

    for alg in ['Prim\'s', 'Kruskal']:
        alg_data = data[(data['Algorithm'] == alg) & (data['Run'] > 5)]

        mean = np.mean(alg_data['Time']) / 1e6
        median = np.median(alg_data['Time']) / 1e6
        sdv = np.std(alg_data['Time']) / 1e6

        print('=====',alg," ", dist, '=====')
        print(alg, " Mean Time: ", f'{mean:.2f}')
        print(alg, " Median: ", f'{median:.2f}')
        print(alg, " Standard Deviation: ", f'{sdv:.2f}')

        axes[idx].plot(alg_data['Run'] - 5, alg_data['Time'] / 1e6, marker='o', label=alg, linewidth=2)
        axes[idx].axhline(y=mean,linestyle='--',color=axes[idx].lines[-1].get_color(),alpha=0.6,linewidth=1.5)

    axes[idx].set_title(f'{dist} Input Distribution', fontsize=12)
    axes[idx].set_xlabel('Runs')
    axes[idx].set_ylabel('Time (ms)' if idx == 0 else '')
    axes[idx].legend()
    axes[idx].grid(True, alpha=0.3)

plt.tight_layout()
plt.show()