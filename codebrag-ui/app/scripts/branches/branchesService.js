angular.module('codebrag.branches')

    .factory('branchesService', function($http, $q, $rootScope, events) {

        var branchesList = [],
            currentBranch,
            dataReady = $q.defer(),
            push = Array.prototype.push;

        function loadAvailableBranches() {
            dataReady = $q.defer();
            return $http.get('rest/branches').then(function useBranches(response) {
                branchesList.length = 0;
                push.apply(branchesList, response.data.branches);
                if(angular.isUndefined(currentBranch)) {
                    currentBranch = response.data.current;
                }
                dataReady.resolve();
                return branchesList;
            });
        }

        function allBranches() {
            if(angular.isUndefined(branchesList)) {
                return loadAvailableBranches();
            } else {
                return $q.when(branchesList);
            }
        }

        function selectBranch(branchName) {
            var found = branchesList.filter(function(branch) {
                return branch === branchName
            });
            if(found.length > 0) {
                currentBranch = found[0];
                $rootScope.$broadcast(events.branches.branchChanged, currentBranch);
            }
        }

        function selectedBranch() {
            return currentBranch;
        }

        function ready() {
            return dataReady.promise;
        }

        return {
            ready: ready,
            fetchBranches: loadAvailableBranches,
            allBranches: allBranches,
            selectBranch: selectBranch,
            selectedBranch: selectedBranch
        }

    });
