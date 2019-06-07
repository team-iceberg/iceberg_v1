(function() {
    'use strict';
    angular
        .module('icebergApp')
        .factory('Emplacement', Emplacement);

    Emplacement.$inject = ['$resource'];

    function Emplacement ($resource) {
        var resourceUrl =  'api/emplacements/:id';

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
