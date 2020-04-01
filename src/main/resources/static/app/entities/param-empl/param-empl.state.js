(function() {
    'use strict';

    angular
        .module('icebergApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('param-empl', {
            parent: 'entity',
            url: '/param-empl',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ParamEmpls'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/param-empl/param-empls.html',
                    controller: 'ParamEmplController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('param-empl-detail', {
            parent: 'param-empl',
            url: '/param-empl/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ParamEmpl'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/param-empl/param-empl-detail.html',
                    controller: 'ParamEmplDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ParamEmpl', function($stateParams, ParamEmpl) {
                    return ParamEmpl.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'param-empl',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('param-empl-detail.edit', {
            parent: 'param-empl-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-empl/param-empl-dialog.html',
                    controller: 'ParamEmplDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParamEmpl', function(ParamEmpl) {
                            return ParamEmpl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('param-empl.new', {
            parent: 'param-empl',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-empl/param-empl-dialog.html',
                    controller: 'ParamEmplDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ref: null,
                                libelle: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('param-empl', null, { reload: 'param-empl' });
                }, function() {
                    $state.go('param-empl');
                });
            }]
        })
        .state('param-empl.edit', {
            parent: 'param-empl',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-empl/param-empl-dialog.html',
                    controller: 'ParamEmplDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParamEmpl', function(ParamEmpl) {
                            return ParamEmpl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('param-empl', null, { reload: 'param-empl' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('param-empl.delete', {
            parent: 'param-empl',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-empl/param-empl-delete-dialog.html',
                    controller: 'ParamEmplDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ParamEmpl', function(ParamEmpl) {
                            return ParamEmpl.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('param-empl', null, { reload: 'param-empl' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
