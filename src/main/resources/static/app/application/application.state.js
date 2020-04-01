(function() {
    'use strict';

    angular
        .module('icebergApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('application', {
            parent: 'app',
            url: '/application',
            data: {
                    authorities: ['ROLE_USER','ROLE_VISITEUR'],
                pageTitle: 'ICEBERG'
            },
            views: {
                'content@': {
                    templateUrl: 'app/application/application.html'
                }
            }
        });
    }
})();

function resizeIframe(obj) {
	console.log(window.innerHeight);
  //  obj.style.height = window.innerHeight-80 + 'px';
}