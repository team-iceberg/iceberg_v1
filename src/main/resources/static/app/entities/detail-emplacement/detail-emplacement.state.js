(function() {
    'use strict';

    angular
        .module('icebergApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('detail-emplacement', {
            parent: 'entity',
            url: '/detail-emplacement?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DetailEmplacements'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/detail-emplacement/detail-emplacements.html',
                    controller: 'DetailEmplacementController',
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
        .state('detail-emplacement-detail', {
            parent: 'detail-emplacement',
            url: '/detail-emplacement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DetailEmplacement'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/detail-emplacement/detail-emplacement-detail.html',
                    controller: 'DetailEmplacementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DetailEmplacement', function($stateParams, DetailEmplacement) {
                    return DetailEmplacement.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'detail-emplacement',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('detail-emplacement-detail.edit', {
            parent: 'detail-emplacement-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/detail-emplacement/detail-emplacement-dialog.html',
                    controller: 'DetailEmplacementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DetailEmplacement', function(DetailEmplacement) {
                            return DetailEmplacement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('detail-emplacement.new', {
            parent: 'detail-emplacement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/detail-emplacement/detail-emplacement-dialog.html',
                    controller: 'DetailEmplacementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                valeurCaracteristique: null,
                                qteEnCours: null,
                                caracteristique: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('detail-emplacement', null, { reload: 'detail-emplacement' });
                }, function() {
                    $state.go('detail-emplacement');
                });
            }]
        })
        .state('detail-emplacement.edit', {
            parent: 'detail-emplacement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/detail-emplacement/detail-emplacement-dialog.html',
                    controller: 'DetailEmplacementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DetailEmplacement', function(DetailEmplacement) {
                            return DetailEmplacement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('detail-emplacement', null, { reload: 'detail-emplacement' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('detail-emplacement.delete', {
            parent: 'detail-emplacement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/detail-emplacement/detail-emplacement-delete-dialog.html',
                    controller: 'DetailEmplacementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DetailEmplacement', function(DetailEmplacement) {
                            return DetailEmplacement.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('detail-emplacement', null, { reload: 'detail-emplacement' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
