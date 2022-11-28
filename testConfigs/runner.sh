#!/bin/bash

echo 'Plain rounds 1000ms'
echo 'Uniform'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_1000ms/uniform_20n_50p.json > /tmp/uniform_20n_50p_1000ms_plain_final.log
echo 'Exponential'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_1000ms/exponential_20n_50p.json > /tmp/exponential_20n_50p_1000ms_plain_final.log
echo 'Random'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_1000ms/random_20n_50p.json > /tmp/random_20n_50p_1000ms_plain_final.log
echo 'Split5050'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_1000ms/split5050_20n_50p.json > /tmp/split5050_20n_50p_1000ms_plain_final.log
echo 'Split6040'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_1000ms/split6040_20n_50p.json > /tmp/split6040_20n_50p_1000ms_plain_final.log
echo 'Split8020'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_1000ms/split8020_20n_50p.json > /tmp/split8020_20n_50p_1000ms_plain_final.log
echo 'Split9010'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_1000ms/split9010_20n_50p.json > /tmp/split9010_20n_50p_1000ms_plain_final.log

echo 'Plain rounds 50ms'
echo 'Uniform'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_50ms/uniform_20n_50p_50ms.json > /tmp/uniform_20n_50p_50ms_plain_final.log
echo 'Exponential'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_50ms/exponential_20n_50p_50ms.json > /tmp/exponential_20n_50p_50ms_plain_final.log
echo 'Random'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_50ms/random_20n_50p_50ms.json > /tmp/random_20n_50p_50ms_plain_final.log
echo 'Split5050'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_50ms/split5050_20n_50p_50ms.json > /tmp/split5050_20n_50p_50ms_plain_final.log
echo 'Split6040'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_50ms/split6040_20n_50p_50ms.json > /tmp/split6040_20n_50p_50ms_plain_final.log
echo 'Split8020'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_50ms/split8020_20n_50p_50ms.json > /tmp/split8020_20n_50p_50ms_plain_final.log
echo 'Split9010'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_50ms/split9010_20n_50p_50ms.json > /tmp/split9010_20n_50p_50ms_plain_final.log

echo 'Plain rounds 100ms'
echo 'Uniform'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_100ms/uniform_20n_50p_100ms.json > /tmp/uniform_20n_50p_100ms_plain_final.log
echo 'Exponential'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_100ms/exponential_20n_50p_100ms.json > /tmp/exponential_20n_50p_100ms_plain_final.log
echo 'Random'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_100ms/random_20n_50p_100ms.json > /tmp/random_20n_50p_100ms_plain_final.log
echo 'Split5050'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_100ms/split5050_20n_50p_100ms.json > /tmp/split5050_20n_50p_100ms_plain_final.log
echo 'Split6040'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_100ms/split6040_20n_50p_100ms.json > /tmp/split6040_20n_50p_100ms_plain_final.log
echo 'Split8020'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_100ms/split8020_20n_50p_100ms.json > /tmp/split8020_20n_50p_100ms_plain_final.log
echo 'Split9010'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c plain_100ms/split9010_20n_50p_100ms.json > /tmp/split9010_20n_50p_100ms_plain_final.log

echo 'Random poison rounds 1000ms'
echo 'Uniform'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_1000ms/uniform_20n_50p_1000ms.json > /tmp/uniform_20n_50p_1000ms_poison_interval_final.log
echo 'Exponential'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_1000ms/exponential_20n_50p_1000ms.json > /tmp/exponential_20n_50p_1000ms_poison_interval_final.log
echo 'Random'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_1000ms/random_20n_50p_1000ms.json > /tmp/random_20n_50p_1000ms_poison_interval_final.log
echo 'Split5050'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_1000ms/split5050_20n_50p_1000ms.json > /tmp/split5050_20n_50p_1000ms_poison_interval_final.log
echo 'Split6040'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_1000ms/split6040_20n_50p_1000ms.json > /tmp/split6040_20n_50p_1000ms_poison_interval_final.log
echo 'Split8020'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_1000ms/split8020_20n_50p_1000ms.json > /tmp/split8020_20n_50p_1000ms_poison_interval_final.log
echo 'Split9010'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_1000ms/split9010_20n_50p_1000ms.json > /tmp/split9010_20n_50p_1000ms_poison_interval_final.log

echo 'Random poison rounds 50ms'
echo 'Uniform'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_50ms/uniform_20n_50p_50ms.json > /tmp/uniform_20n_50p_50ms_poison_interval_final.log
echo 'Exponential'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_50ms/exponential_20n_50p_50ms.json > /tmp/exponential_20n_50p_50ms_poison_interval_final.log
echo 'Random'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_50ms/random_20n_50p_50ms.json > /tmp/random_20n_50p_50ms_poison_interval_final.log
echo 'Split5050'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_50ms/split5050_20n_50p_50ms.json > /tmp/split5050_20n_50p_50ms_poison_interval_final.log
echo 'Split6040'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_50ms/split6040_20n_50p_50ms.json > /tmp/split6040_20n_50p_50ms_poison_interval_final.log
echo 'Split8020'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_50ms/split8020_20n_50p_50ms.json > /tmp/split8020_20n_50p_50ms_poison_interval_final.log
echo 'Split9010'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_50ms/split9010_20n_50p_50ms.json > /tmp/split9010_20n_50p_50ms_poison_interval_final.log

echo 'Random poison rounds 100ms'
echo 'Uniform'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_100ms/uniform_20n_50p_100ms.json > /tmp/uniform_20n_50p_100ms_poison_interval_final.log
echo 'Exponential'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_100ms/exponential_20n_50p_100ms.json > /tmp/exponential_20n_50p_100ms_poison_interval_final.log
echo 'Random'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_100ms/random_20n_50p_100ms.json > /tmp/random_20n_50p_100ms_poison_interval_final.log
echo 'Split5050'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_100ms/split5050_20n_50p_100ms.json > /tmp/split5050_20n_50p_100ms_poison_interval_final.log
echo 'Split6040'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_100ms/split6040_20n_50p_100ms.json > /tmp/split6040_20n_50p_100ms_poison_interval_final.log
echo 'Split8020'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_100ms/split8020_20n_50p_100ms.json > /tmp/split8020_20n_50p_100ms_poison_interval_final.log
echo 'Split9010'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c interval_node_poison_100ms/split9010_20n_50p_100ms.json > /tmp/split9010_20n_50p_100ms_poison_interval_final.log

echo '11 poison rounds 1000ms'
echo 'Uniform'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_1000ms/uniform_20n_50p_1000ms.json > /tmp/uniform_20n_50p_1000ms_poison_all_once_final.log
echo 'Exponential'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_1000ms/exponential_20n_50p_1000ms.json > /tmp/exponential_20n_50p_1000ms_poison_all_once_final.log
echo 'Random'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_1000ms/random_20n_50p_1000ms.json > /tmp/random_20n_50p_1000ms_poison_all_once_final.log
echo 'Split5050'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_1000ms/split5050_20n_50p_1000ms.json > /tmp/split5050_20n_50p_1000ms_poison_all_once_final.log
echo 'Split6040'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_1000ms/split6040_20n_50p_1000ms.json > /tmp/split6040_20n_50p_1000ms_poison_all_once_final.log
echo 'Split8020'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_1000ms/split8020_20n_50p_1000ms.json > /tmp/split8020_20n_50p_1000ms_poison_all_once_final.log
echo 'Split9010'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_1000ms/split9010_20n_50p_1000ms.json > /tmp/split9010_20n_50p_1000ms_poison_all_once_final.log

echo '11 poison rounds 50ms'
echo 'Uniform'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_50ms/uniform_20n_50p_50ms.json > /tmp/uniform_20n_50p_50ms_poison_all_once_final.log
echo 'Exponential'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_50ms/exponential_20n_50p_50ms.json > /tmp/exponential_20n_50p_50ms_poison_all_once_final.log
echo 'Random'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_50ms/random_20n_50p_50ms.json > /tmp/random_20n_50p_50ms_poison_all_once_final.log
echo 'Split5050'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_50ms/split5050_20n_50p_50ms.json > /tmp/split5050_20n_50p_50ms_poison_all_once_final.log
echo 'Split6040'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_50ms/split6040_20n_50p_50ms.json > /tmp/split6040_20n_50p_50ms_poison_all_once_final.log
echo 'Split8020'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_50ms/split8020_20n_50p_50ms.json > /tmp/split8020_20n_50p_50ms_poison_all_once_final.log
echo 'Split9010'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_50ms/split9010_20n_50p_50ms.json > /tmp/split9010_20n_50p_50ms_poison_all_once_final.log

echo '11 poison rounds 100ms'
echo 'Uniform'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_100ms/uniform_20n_50p_100ms.json > /tmp/uniform_20n_50p_100ms_poison_all_once_final.log
echo 'Exponential'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_100ms/exponential_20n_50p_100ms.json > /tmp/exponential_20n_50p_100ms_poison_all_once_final.log
echo 'Random'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_100ms/random_20n_50p_100ms.json > /tmp/random_20n_50p_100ms_poison_all_once_final.log
echo 'Split5050'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_100ms/split5050_20n_50p_100ms.json > /tmp/split5050_20n_50p_100ms_poison_all_once_final.log
echo 'Split6040'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_100ms/split6040_20n_50p_100ms.json > /tmp/split6040_20n_50p_100ms_poison_all_once_final.log
echo 'Split8020'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_100ms/split8020_20n_50p_100ms.json > /tmp/split8020_20n_50p_100ms_poison_all_once_final.log
echo 'Split9010'
java -jar ../target/JPaxos-1.0-SNAPSHOT-shaded.jar -c all_once_node_poison_100ms/split9010_20n_50p_100ms.json > /tmp/split9010_20n_50p_100ms_poison_all_once_final.log
