(function() {
    'use strict';

    angular
        .module('icebergApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('objet', {
            parent: 'entity',
            url: '/objet?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Objets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/objet/objets.html',
                    controller: 'ObjetController',
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
        .state('objet-detail', {
            parent: 'objet',
            url: '/objet/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Objet'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/objet/objet-detail.html',
                    controller: 'ObjetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Objet', function($stateParams, Objet) {
                    return Objet.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'objet',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('objet-detail.edit', {
            parent: 'objet-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/objet/objet-dialog.html',
                    controller: 'ObjetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Objet', function(Objet) {
                            return Objet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('objet.new', {
            parent: 'objet',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/objet/objet-dialog.html',
                    controller: 'ObjetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libelle: null,
                                dateDepot: null,
                                image1: null,
                                image1ContentType: null,
                                image2: null,
                                image2ContentType: null,
                                image3: null,
                                image3ContentType: null,
                                image4: null,
                                image4ContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('objet', null, { reload: 'objet' });
                }, function() {
                    $state.go('objet');
                });
            }]
        })
        .state('objet.edit', {
            parent: 'objet',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/objet/objet-dialog.html',
                    controller: 'ObjetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Objet', function(Objet) {
                            return Objet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('objet', null, { reload: 'objet' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('objet.delete', {
            parent: 'objet',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/objet/objet-delete-dialog.html',
                    controller: 'ObjetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Objet', function(Objet) {
                            return Objet.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('objet', null, { reload: 'objet' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
