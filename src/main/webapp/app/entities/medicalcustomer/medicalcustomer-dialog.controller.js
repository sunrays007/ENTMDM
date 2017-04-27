(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .controller('MedicalcustomerDialogController', MedicalcustomerDialogController);

    MedicalcustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Medicalcustomer'];

    function MedicalcustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Medicalcustomer) {
        var vm = this;

        vm.medicalcustomer = entity;
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
            if (vm.medicalcustomer.id !== null) {
                Medicalcustomer.update(vm.medicalcustomer, onSaveSuccess, onSaveError);
            } else {
                Medicalcustomer.save(vm.medicalcustomer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('entmdmApp:medicalcustomerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
