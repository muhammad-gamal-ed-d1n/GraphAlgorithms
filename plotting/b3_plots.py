import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

data = pd.read_csv('sssp_dag.csv')

algs = data['Algorithm'].unique()

fig, ax = plt.subplots(figsize=(15, 5))

fig.suptitle("Dijkstra vs DAGShortestPath")

dijkstra_mean = 0
dagp_mean = 0

for alg in algs:
    alg_data = data[(data['Algorithm'] == alg) & (data['Run'] > 5)]

    mean = np.mean(alg_data['Time'][5:]) / 1e6
    median = np.median(alg_data['Time'][5:]) / 1e6
    std = np.std(alg_data['Time'][5:]) / 1e6

    if alg == 'Dijkstra':
        dijkstra_mean = mean
    else:
        dagp_mean = mean

    print('===', alg, '====')
    print('Mean: ',f'{mean:.2f}')
    print('Median: ',f'{median:.2f}')
    print('Standard Deviation: ',f'{std:.2f}')


    ax.plot(alg_data['Run'] - 5, alg_data['Time'] / 1e6, label=alg, linewidth=2)

print('Improvement %: ', f'{((dijkstra_mean / dagp_mean) * 100):.2f}')

ax.legend()
ax.set_ylabel("Time (ms)")
ax.set_xlabel("Runs")

plt.tight_layout()
plt.show()