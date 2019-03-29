function selectTest(obj,type) {
    if(type==1){

        $("#branch").val(obj.options[obj.selectedIndex].innerHTML);
    }else{
        $("#ausAgent").val(obj.options[obj.selectedIndex].innerHTML);
    }

}