package com.epam.rd.autocode.observer.git;
import java.util.*;

public class GitRepoObservers {
    public static Repository newRepository(){
        return new RepositoryImpl();
    }

    public static WebHook mergeToBranchWebHook(String branchName){
        return new WebHookImpl(branchName, Event.Type.MERGE);
    }

    public static WebHook commitToBranchWebHook(String branchName){
        return new WebHookImpl(branchName, Event.Type.COMMIT);
    }
    private static class RepositoryImpl implements Repository {
        private final List<WebHook> hooks = new ArrayList<>();
        private final List<CommitRecord> commits = new ArrayList<>();

        @Override
        public void addWebHook(WebHook webHook) {
            hooks.add(webHook);
        }

        @Override
        public Commit commit(String branch, String author, String[] changes) {
            Commit commit = new Commit(author, changes);
            commits.add(new CommitRecord(branch, commit));

            Event event = new Event(Event.Type.COMMIT, branch, List.of(commit));
            hooks.forEach(h -> {
                if (h.branch().equals(branch) && h.type() == Event.Type.COMMIT) {
                    h.onEvent(event);
                }
            });
            return commit;
        }

        @Override
        public void merge(String sourceBranch, String targetBranch) {
            Set<Commit> targetBranchCommits = new HashSet<>();
            for (CommitRecord record : commits) {
                if (record.branch.equals(targetBranch)) {
                    targetBranchCommits.add(record.commit);
                }
            }

            // Collect new commits from source branch
            List<CommitRecord> newCommitsToAdd = new ArrayList<>();
            List<Commit> newCommitsForMerge = new ArrayList<>();
            for (CommitRecord record : commits) {
                if (record.branch.equals(sourceBranch) && !targetBranchCommits.contains(record.commit)) {
                    newCommitsToAdd.add(new CommitRecord(targetBranch, record.commit));
                    newCommitsForMerge.add(record.commit);
                }
            }

            commits.addAll(newCommitsToAdd);

            if (!newCommitsForMerge.isEmpty()) {
                Event event = new Event(Event.Type.MERGE, targetBranch, newCommitsForMerge);
                for (WebHook h : hooks) {
                    if (h.branch().equals(targetBranch) && h.type() == Event.Type.MERGE) {
                        h.onEvent(event);
                    }
                }
            }
        }

        // Helper class to track commits per branch
        private static class CommitRecord {
            String branch;
            Commit commit;

            CommitRecord(String branch, Commit commit) {
                this.branch = branch;
                this.commit = commit;
            }
        }
    }

    // WebHook implementation
    private static class WebHookImpl implements WebHook {
        private final String branch;
        private final Event.Type type;
        private final List<Event> caughtEvents = new ArrayList<>();

        WebHookImpl(String branch, Event.Type type) {
            this.branch = branch;
            this.type = type;
        }

        @Override
        public String branch() {
            return branch;
        }

        @Override
        public Event.Type type() {
            return type;
        }

        @Override
        public List<Event> caughtEvents() {
            return Collections.unmodifiableList(caughtEvents);
        }

        @Override
        public void onEvent(Event event) {
            caughtEvents.add(event);
        }
    }


}
