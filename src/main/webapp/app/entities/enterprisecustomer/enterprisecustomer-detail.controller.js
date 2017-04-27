(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .controller('EnterprisecustomerDetailController', EnterprisecustomerDetailController);

    EnterprisecustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Enterprisecustomer'];

    function EnterprisecustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, Enterprisecustomer) {
        var vm = this;

        vm.enterprisecustomer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('entmdmApp:enterprisecustomerUpdate', function(event, result) {
            vm.enterprisecustomer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
