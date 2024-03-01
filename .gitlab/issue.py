import os
import sys
import requests
from csv2md import table
import csv

commit = sys.argv[1]
path_to_smells = "smells/"
smell_files = [f for f in os.listdir(path_to_smells) if os.path.isfile(
    os.path.join(path_to_smells, f)) and str(f).strip().endswith(".csv")]

headers = {
    "PRIVATE-TOKEN": "glpat-G9rmsfyuS-_Fxu6y68p9"
}
for sf in smell_files:

    with open(os.path.join(path_to_smells, sf)) as csv_file:
        list_smells = list(csv.reader(csv_file))

    raw_md = table.Table(list_smells).markdown()

    title = str(sf).replace(".csv", "")+" for commit - "+str(commit)
    query_params = f"title={title}&labels=quality"
    body = {
        "description": {raw_md}
    }
    gitlab_output = requests.post(
        f"https://git.cs.dal.ca/api/v4/projects/{77385}/issues?{query_params}", headers=headers, data=body)
    if not gitlab_output.status_code == 201:
        raise Exception
