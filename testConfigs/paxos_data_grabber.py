#!/usr/bin/python3
import glob, os
import re

def process_all_files():
    for filename in glob.glob("*final.log"):
        process_file(filename)

def process_file(filename):
    content = open(filename).read()

    if 'poison' in filename:
        poison = 'true'
    else:
        poison = 'false'

    all_messages = re.findall(r'received', content) 
    total_messages = len(all_messages)
    #print(total_messages)

    number_nodes = int(re.findall(r'numberNodes: (\d+)', content)[0])
    #print(number_nodes)

    #number_proposals = len(re.findall(r'Sending propose', content))
    number_proposals = int(re.findall(r'numberProposals: (\d+)', content)[0])
    #print(number_proposals)

    proposal_delay = int(re.findall(r'proposalDelay: (\d+)', content)[0])
    #print(proposal_delay)

    weight_type = re.findall(r'weightType: (\w+)', content)[0]
    #print(weight_type)

    poison_all_at_once = re.findall(r'poisonAllAtOnce: (\w+)', content)[0]
    #print(poison_all_at_once)

    poison_frequency = int(re.findall(r'poisonFrequency: (\d+)', content)[0])
    #print(poison_frequency)

    poison_time = int(re.findall(r'poisonTime: (\d+)', content)[0])
    #print(poison_time)

    total_time = int(re.findall(r'Total time in us: (\d+)', content)[0])
    #print(total_time)

    message_processing_time = (total_time - (number_proposals * proposal_delay / 1000))/1000

    all_decided_acks = re.findall(r'Decided on a value with (\d+) acks', content)
    all_decided_acks = [int(i) for i in all_decided_acks]
    decided_count = len(all_decided_acks)
    decided_average = sum(all_decided_acks) / decided_count
    proposal_loss = (number_proposals - decided_count)/number_proposals
    #print(all_decided_acks)
    #print(decided_count)
    #print(decided_average)
    #print(proposal_loss)
    print(filename, ",", number_nodes, ",", number_proposals, ",", proposal_delay, ",", 
            weight_type, ",", poison, ",", poison_all_at_once, ",", poison_frequency, ",",
            poison_time, ",", decided_average, ",", proposal_loss, ",",
            total_messages, ",", total_time, ",", message_processing_time, sep="")

if __name__ == "__main__":
    header = 'Filename,Number Nodes,Number Proposals,Proposal Delay (ms),Weight type,Poison,' \
            'Poison All at Once,Poison Frequency (ms),Poison Time (ms),Average Responses to Decide,' \
            'Proposal Loss (%),Total Number Messages,Total Time (us),Message processing time (ms)'
    print(header)
    process_all_files()
    #process_file("split9010_20n_50p_100ms_poison_all_once_final.log")
    
