def call (String scmBranch) {
    checkout(
		$class: 'GitSCM',
		branches: [[name: scmBranch]],
		userRemoteConfigs: [[
			url: 'https://github.com/Mahesh-Northgate/test-codacy.git'
		]])

	// Wipe out _all_ untracked, ignored and changed files (so it's like a fresh clone)
	// Note: `git config --local` is persisted in "${env:workspace}/.git/config"
	// Note: some, but not all, untracked files are also removed at the end of the build
	// See also: CON-93873 Clean Jenkins workspace between builds
	script {
		bat """
			cd "${env:workspace}"
			git config --local core.longpaths true
			git clean -xdf
			git reset --hard
		"""
	}
}
