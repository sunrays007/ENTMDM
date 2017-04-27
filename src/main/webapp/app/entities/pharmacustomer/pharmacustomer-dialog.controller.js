(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .controller('PharmacustomerDialogController', PharmacustomerDialogController);

    PharmacustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pharmacustomer'];

    function PharmacustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pharmacustomer) {
        var vm = this;

        vm.pharmacustomer = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pharmacustomer.id !== null) {
                Pharmacustomer.update(vm.pharmacustomer, onSaveSuccess, onSaveError);
            } else {
                Pharmacustomer.save(vm.pharmacustomer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('entmdmApp:pharmacustomerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
