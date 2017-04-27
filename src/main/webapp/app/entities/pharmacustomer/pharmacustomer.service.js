(function() {
    'use strict';
    angular
        .module('entmdmApp')
        .factory('Pharmacustomer', Pharmacustomer);

    Pharmacustomer.$inject = ['$resource'];

    function Pharmacustomer ($resource) {
        var resourceUrl =  'api/pharmacustomers/:id';

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
