(function() {
    'use strict';

    angular
        .module('icebergApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('emplacement', {
            parent: 'entity',
            url: '/emplacement?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Emplacements'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emplacement/emplacements.html',
                    controller: 'EmplacementController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('emplacement-detail', {
            parent: 'emplacement',
            url: '/emplacement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Emplacement'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emplacement/emplacement-detail.html',
                    controller: 'EmplacementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Emplacement', function($stateParams, Emplacement) {
                    return Emplacement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'emplacement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('emplacement-detail.edit', {
            parent: 'emplacement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emplacement/emplacement-dialog.html',
                    controller: 'EmplacementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Emplacement', function(Emplacement) {
                            return Emplacement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emplacement.new', {
            parent: 'emplacement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emplacement/emplacement-dialog.html',
                    controller: 'EmplacementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                qteTotal: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('emplacement', null, { reload: 'emplacement' });
                }, function() {
                    $state.go('emplacement');
                });
            }]
        })
        .state('emplacement.edit', {
            parent: 'emplacement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emplacement/emplacement-dialog.html',
                    controller: 'EmplacementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Emplacement', function(Emplacement) {
                            return Emplacement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emplacement', null, { reload: 'emplacement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emplacement.delete', {
            parent: 'emplacement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emplacement/emplacement-delete-dialog.html',
                    controller: 'EmplacementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Emplacement', function(Emplacement) {
                            return Emplacement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emplacement', null, { reload: 'emplacement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
