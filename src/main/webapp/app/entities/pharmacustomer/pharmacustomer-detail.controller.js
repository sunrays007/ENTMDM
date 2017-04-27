(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .controller('PharmacustomerDetailController', PharmacustomerDetailController);

    PharmacustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pharmacustomer'];

    function PharmacustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, Pharmacustomer) {
        var vm = this;

        vm.pharmacustomer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('entmdmApp:pharmacustomerUpdate', function(event, result) {
            vm.pharmacustomer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
