## TradeCard Project rules for developers

We are going to follow the monorepo approach for our development.
We will have two main development folders: frontend (React code) and Backend (Java Code).

For both the folders, we are going to follow below common rules:

- There will be 3 branches where we will do our work:

  - **Main branch** - production branch
  - **Staging branch** - testing branch
  - **Dev branch** - development branch

- All the new development will take place on dev branch. You will always create a new branch for your development task from the Dev branch.
- Branch naming convention: **IssueID_BranchPurpose**
- Always create a PR for your changes. Tag the reviewers in your PR. Post a message in teams channel to request a review for your PR, mention your PR link and issue link.
- Whoever reviews PR must mention comments if needed in PR and send a message in teams thread for the same. If PR looks good, mention in teams thread and approve the PR.
- We will **merge changes** from **Dev branch** to **Staging branch every week**.
- We will **merge changes** from **Staging branch** to **main branch every 2 weeks**.
- Whenver pushing changes to main branch, update the changelog file with all the relevant changes and update the version number of the project.
