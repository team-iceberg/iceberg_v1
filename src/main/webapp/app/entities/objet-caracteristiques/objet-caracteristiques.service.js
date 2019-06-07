(function() {
    'use strict';
    angular
        .module('icebergApp')
        .factory('ObjetCaracteristiques', ObjetCaracteristiques);

    ObjetCaracteristiques.$inject = ['$resource'];

    function ObjetCaracteristiques ($resource) {
        var resourceUrl =  'api/objet-caracteristiques/:id';

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
