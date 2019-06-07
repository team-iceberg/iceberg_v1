(function() {
    'use strict';

    angular
        .module('icebergApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('param-couleur', {
            parent: 'entity',
            url: '/param-couleur',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ParamCouleurs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/param-couleur/param-couleurs.html',
                    controller: 'ParamCouleurController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('param-couleur-detail', {
            parent: 'param-couleur',
            url: '/param-couleur/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ParamCouleur'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/param-couleur/param-couleur-detail.html',
                    controller: 'ParamCouleurDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ParamCouleur', function($stateParams, ParamCouleur) {
                    return ParamCouleur.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'param-couleur',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('param-couleur-detail.edit', {
            parent: 'param-couleur-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-couleur/param-couleur-dialog.html',
                    controller: 'ParamCouleurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParamCouleur', function(ParamCouleur) {
                            return ParamCouleur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('param-couleur.new', {
            parent: 'param-couleur',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-couleur/param-couleur-dialog.html',
                    controller: 'ParamCouleurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                couleur: null,
                                codeHexa: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('param-couleur', null, { reload: 'param-couleur' });
                }, function() {
                    $state.go('param-couleur');
                });
            }]
        })
        .state('param-couleur.edit', {
            parent: 'param-couleur',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-couleur/param-couleur-dialog.html',
                    controller: 'ParamCouleurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParamCouleur', function(ParamCouleur) {
                            return ParamCouleur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('param-couleur', null, { reload: 'param-couleur' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('param-couleur.delete', {
            parent: 'param-couleur',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/param-couleur/param-couleur-delete-dialog.html',
                    controller: 'ParamCouleurDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ParamCouleur', function(ParamCouleur) {
                            return ParamCouleur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('param-couleur', null, { reload: 'param-couleur' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
