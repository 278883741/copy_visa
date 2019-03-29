function commissionSchool() {
    if($("#collegeId").val()!=0){
        $.ajax({
            url:"/visaRecord/getCooperation",
            method:"post",
            data:{"schoolID":$("#collegeId").val()},
            success:function(data){
                $("#agencyNo").empty();
                $.each(data, function (index, item) {
                    $("#agencyNo").append($("<option value=" + item.id + ">" + item.name + "</option>"));
                    $("#agencyNo").change(function(){
                        $("#agency_no").val($("#agencyNo").find("option:selected").val());
                        $("#agency_name").val($("#agencyNo").find("option:selected").text());
                    });
                    $("#agency_no").val($("#agencyNo").find("option:selected").val());
                    $("#agency_name").val($("#agencyNo").find("option:selected").text());

                });
                $(".chosen-select").trigger("chosen:updated");
            }
        })
    }
}


function commissionSchoolId() {
    if($("#college").val()!=0){
        $.ajax({
            url:"/visaRecord/getCooperation",
            method:"post",
            data:{"schoolID":$("#college").val()},
            success:function(data){
                $("#agencyNo").empty();
                $.each(data, function (index, item) {
                    $("#agencyNo").append($("<option value=" + item.id + ">" + item.name + "</option>"));
                    $("#agencyNo").change(function(){
                        $("#agency_no").val($("#agencyNo").find("option:selected").val());
                        $("#agency_name").val($("#agencyNo").find("option:selected").text());
                    });
                    $("#agency_no").val($("#agencyNo").find("option:selected").val());
                    $("#agency_name").val($("#agencyNo").find("option:selected").text());

                });
                $(".chosen-select").trigger("chosen:updated");
            }
        })
    }
}