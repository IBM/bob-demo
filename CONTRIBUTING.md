# Contributing to the IBM Bob Demo Repository

1. Contact the team via the [discussion forum](https://github.com/IBM/bob-demo/discussions) and propose a new demo or discuss your suggested feature.

2. [Fork](https://github.com/IBM/bob-demo) the project. This creates the `bob-demo` project in your own Git with the default remote name 'origin'.

3. Clone your fork. This creates and populates a directory in your local file system.

        git clone https://github.com/<your-username>/bob-demo.git

4. Change to the `bob-demo` directory.

5. Add the remote `upstream` repository so you can fetch any changes to the original forked repository.

        git remote add upstream https://github.com/IBM/bob-demo.git

6. Get the latest files from the `upstream` repository.

        git fetch upstream

7. Create a local topic branch to work with your new quickstart, features, changes, or fixes. 
   
        git checkout -b  <topic-branch-name>
   * For new demos or other fixes, try to use a good description for the branch name. 
   * The following are examples of Git checkout commands:

            git checkout -b new_demo_name
            
8. Contribute new code or make changes to existing files.

9. Use the `git add` command to add new or changed file contents to the staging area.
     
10. Use the git status command to view the status of the files in the directory and in the staging area and ensure that all modified files are properly staged:

        git status      
          
11. Commit your changes to your local topic branch. 

        git commit -m 'Description of change/addition...'
        
12. Update your branch with any changes made upstream since you started.
   * Fetch the latest changes from upstream

        git fetch upstream
        
   * Apply those changes to your branch
   
        git rebase upstream/main
        
   * If anyone has commited changes to files that you have also changed, you may see conflicts. 
   Resolve the conflicted files, add them using `git add`, and continue the rebase:
   
        git add <conflicted-file-name>
        git rebase --continue
        
   * If there were conflicts, it is a good idea to test your changes again to make they still work.
        
13. Push your local topic branch to your GitHub forked repository. This will create a branch on your Git fork repository with the same name as your local topic branch name. 

        git push origin HEAD            
   _Note:_ The above command assumes your remote repository is named 'origin'. You can verify your forked remote repository name using the command `git remote -v`.
   
14. Raise a Pull Request by:
 1. Visit https://github.com/IBM/bob-demo
 2. Click on the "Compare and Pull Request" button next to your topic branch.
 3. Review your code changes and check that only the commit(s) you expect are present.
 4. Check that the PR title contains the JIRA ID and a short description.
 5. Click "Create Pull Request"

The Pull request will then be reviewed by the IBM Developer Advocacy team, and you may be requested to make changes. To make a change:

1. Ensure the clone on your computer is on the topic branch for this issue
2. Make the requested changes and commit them.
3. Push your branch to your fork on GitHub:

    git push origin new_demo_name

4. Comment on the PR to indicate that you think you have made the requested changes.