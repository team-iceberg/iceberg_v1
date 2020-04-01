(function() {
    'use strict';

    angular
        .module('icebergApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('param-type', {
            parent: 'entity',
            url: '/param-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ParamTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/param-type/param-types.html',
                    controller: 'ParamTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('param-type-detail', {
            parent: 'param-type',
            url: '/param-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ParamType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/param-type/param-type-detail.html',
                    controller: 'ParamTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ParamType', function($stateParams, ParamType) {
                    return ParamType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'param-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('param-type-detail.edit', {
            parent: 'param-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-type/param-type-dialog.html',
                    controller: 'ParamTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParamType', function(ParamType) {
                            return ParamType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('param-type.new', {
            parent: 'param-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-type/param-type-dialog.html',
                    controller: 'ParamTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type: null,
                                libelle: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('param-type', null, { reload: 'param-type' });
                }, function() {
                    $state.go('param-type');
                });
            }]
        })
        .state('param-type.edit', {
            parent: 'param-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-type/param-type-dialog.html',
                    controller: 'ParamTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParamType', function(ParamType) {
                            return ParamType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('param-type', null, { reload: 'param-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('param-type.delete', {
            parent: 'param-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-type/param-type-delete-dialog.html',
                    controller: 'ParamTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ParamType', function(ParamType) {
                            return ParamType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('param-type', null, { reload: 'param-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
