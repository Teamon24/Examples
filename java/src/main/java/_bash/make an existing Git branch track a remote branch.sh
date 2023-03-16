var REMOTE_URL = change/to/remote/url
git remote add upstream REMOTE_URL
git fetch upstream
git branch -f --track qa upstream/qa
# OR Git version 1.8.0 and higher:
git branch --set-upstream-to=upstream/qa
# Gitversions lower than 1.8.0
git branch --set-upstream qa upstream/qa