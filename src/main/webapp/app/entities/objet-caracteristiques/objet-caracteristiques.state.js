(function() {
    'use strict';

    angular
        .module('icebergApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('objet-caracteristiques', {
            parent: 'entity',
            url: '/objet-caracteristiques?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ObjetCaracteristiques'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/objet-caracteristiques/objet-caracteristiques.html',
                    controller: 'ObjetCaracteristiquesController',
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
        .state('objet-caracteristiques-detail', {
            parent: 'objet-caracteristiques',
            url: '/objet-caracteristiques/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ObjetCaracteristiques'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/objet-caracteristiques/objet-caracteristiques-detail.html',
                    controller: 'ObjetCaracteristiquesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ObjetCaracteristiques', function($stateParams, ObjetCaracteristiques) {
                    return ObjetCaracteristiques.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'objet-caracteristiques',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('objet-caracteristiques-detail.edit', {
            parent: 'objet-caracteristiques-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/objet-caracteristiques/objet-caracteristiques-dialog.html',
                    controller: 'ObjetCaracteristiquesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ObjetCaracteristiques', function(ObjetCaracteristiques) {
                            return ObjetCaracteristiques.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('objet-caracteristiques.new', {
            parent: 'objet-caracteristiques',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/objet-caracteristiques/objet-caracteristiques-dialog.html',
                    controller: 'ObjetCaracteristiquesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                valeur: null,
                                caracteristique: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('objet-caracteristiques', null, { reload: 'objet-caracteristiques' });
                }, function() {
                    $state.go('objet-caracteristiques');
                });
            }]
        })
        .state('objet-caracteristiques.edit', {
            parent: 'objet-caracteristiques',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/objet-caracteristiques/objet-caracteristiques-dialog.html',
                    controller: 'ObjetCaracteristiquesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ObjetCaracteristiques', function(ObjetCaracteristiques) {
                            return ObjetCaracteristiques.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('objet-caracteristiques', null, { reload: 'objet-caracteristiques' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('objet-caracteristiques.delete', {
            parent: 'objet-caracteristiques',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/objet-caracteristiques/objet-caracteristiques-delete-dialog.html',
                    controller: 'ObjetCaracteristiquesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ObjetCaracteristiques', function(ObjetCaracteristiques) {
                            return ObjetCaracteristiques.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('objet-caracteristiques', null, { reload: 'objet-caracteristiques' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
