import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

data = pd.read_csv('sssp_general.csv')

distributions = data['Distribution'].unique()

fig, ax = plt.subplots(figsize=(15, 5))

fig.suptitle('Dijkstra Benchmark', fontsize=16)

for dist in distributions:
    alg_data = data[(data['Distribution'] == dist) & (data['Run'] > 5)]

    mean = np.mean(alg_data['Time']) / 1e6
    median = np.median(alg_data['Time']) / 1e6
    std = np.std(alg_data['Time']) / 1e6

    print('====', dist, '====')
    print("Mean: ", f'{mean:.2f}')
    print("Median: ", f'{median:.2f}')
    print("Standard Deviation: ", f'{std:.2f}')

    ax.plot(alg_data['Run'], alg_data['Time'] / 1e6, label=dist, linewidth=2)
    ax.axhline(y=mean,linestyle='--',color=ax.lines[-1].get_color(),alpha=0.6,linewidth=1.5)

ax.set_ylabel('Time (ms)')
ax.set_xlabel('Runs')
ax.legend()

plt.tight_layout()
plt.show()