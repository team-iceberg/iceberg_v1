(function() {
    'use strict';
    angular
        .module('icebergApp')
        .factory('ParamType', ParamType);

    ParamType.$inject = ['$resource'];

    function ParamType ($resource) {
        var resourceUrl =  'api/param-types/:id';

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
