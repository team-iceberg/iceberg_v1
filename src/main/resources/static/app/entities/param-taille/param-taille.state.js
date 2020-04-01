(function() {
    'use strict';

    angular
        .module('icebergApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('param-taille', {
            parent: 'entity',
            url: '/param-taille',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ParamTailles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/param-taille/param-tailles.html',
                    controller: 'ParamTailleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('param-taille-detail', {
            parent: 'param-taille',
            url: '/param-taille/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ParamTaille'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/param-taille/param-taille-detail.html',
                    controller: 'ParamTailleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ParamTaille', function($stateParams, ParamTaille) {
                    return ParamTaille.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'param-taille',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('param-taille-detail.edit', {
            parent: 'param-taille-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-taille/param-taille-dialog.html',
                    controller: 'ParamTailleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParamTaille', function(ParamTaille) {
                            return ParamTaille.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('param-taille.new', {
            parent: 'param-taille',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-taille/param-taille-dialog.html',
                    controller: 'ParamTailleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                taille: null,
                                libelle: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('param-taille', null, { reload: 'param-taille' });
                }, function() {
                    $state.go('param-taille');
                });
            }]
        })
        .state('param-taille.edit', {
            parent: 'param-taille',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-taille/param-taille-dialog.html',
                    controller: 'ParamTailleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParamTaille', function(ParamTaille) {
                            return ParamTaille.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('param-taille', null, { reload: 'param-taille' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('param-taille.delete', {
            parent: 'param-taille',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-taille/param-taille-delete-dialog.html',
                    controller: 'ParamTailleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ParamTaille', function(ParamTaille) {
                            return ParamTaille.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('param-taille', null, { reload: 'param-taille' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
