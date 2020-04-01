(function() {
    'use strict';
    angular
        .module('icebergApp')
        .factory('ParamCouleur', ParamCouleur);

    ParamCouleur.$inject = ['$resource'];

    function ParamCouleur ($resource) {
        var resourceUrl =  'api/param-couleurs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
