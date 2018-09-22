function readCSVFile(csv, delimeter = ";") {
    var allTextLines = csv.split(/\r\n|\n/);
    var lines = [];
    for (var i = 0; i < allTextLines.length; i++) {
        var data = allTextLines[i].split(delimeter);
        var tarr = [];
        for (var j = 0; j < data.length; j++) {
            tarr.push(data[j]);
        }
        lines.push(tarr);
    }
    return lines;
}


function checkCSVExtension(file_upload_id, error_lbl_id, append_container, option, branch_id) {
    var file_upload_el = $("#" + file_upload_id);
    var error_el = $("#" + error_lbl_id);
    var file_name = file_upload_el.prop("files")[0]["name"];
    var file_extension = file_name.split('.').pop();

    if (file_extension !== "csv") {
        $(error_el).html("<span style='color:red;'>Please insert a valid csv file!</span>");
    } else {
        // Read file contents
        $(error_el).html("<span style='color:green;'>Reading file contents...</span>");
        var reader = new FileReader();
        reader.readAsText(file_upload_el.prop("files")[0]);
        reader.onload = function (event) {
            var csv = event.target.result;
            var data = readCSVFile(csv, ",");

            // validate template format
            //var header = data[1];

            if (option === 'tutor') {
                var header = data[1];
                if (header.length === 8 && header[0] === "Tutor NRIC" && header[1] === "Full Name"
                        && header[2] === "Phone" && header[3] === "Address"
                        && header[4] === "Birth Date (DD-MM-YYYY)"
                        && header[5] === "Gender (F/M)" && header[6] === "Email" && header[7] === "Hourly Rate") {
                    $("#" + append_container).html("");
                    processCSVTutorData(data, append_container, error_el, branch_id);
                } else {
                    $(error_el).html("<span style='color:red;'>Invalid Template " + (header[0]) + "</span>");
                }
            } else {
                var header = data[1];
                if (header.length === 19 && header[0] === "Student NRIC" && header[1] === "Student Name" && header[2] === "Phone"
                        && header[3] === "Address" && header[4] === "Birth Date (DD-MM-YYYY)" && header[5] === "Gender (F/M)"
                        && header[6] === "Email" && header[7] === "School" && header[8] === "Academic Level" && header[9] === "Parent Name" && header[10] === "Relationship"
                        && header[11] === "Parent Nationality" && header[12] === "Parent Company" && header[13] === "Parent Designation" && header[14] === "Parent Mobile" && header[15] === "Parent Email" 
                        && header[16] === "Registration fee ($)" && header[17] === "Outstanding Registration Fee ($)" && header[18] === "Student Status (Existing (E)/New (N))") {
                    $("#" + append_container).html("");
                    processCSVStudentData(data, append_container, error_el);
                } else {
                    $(error_el).html("<span style='color:red;'>Invalid Template" + (header.length) + "</span>");
                }
            }
        };
        $(file_upload_el).val("");
    }
}


function processCSVTutorData(csv_data, append_container, error_el, branch_id) {
    console.log(branch_id);
    var html = "";
    if (csv_data.length > 0) {
        html += "<div class='row'>" +
                "<div class='col-sm-1'></div>" +
                "<div class='col-sm-2 bold'>" +
                "Tutor Name" +
                "</div>" +
                "<div class='col-sm-3 bold'>" +
                "Phone" +
                "</div>" +
                "<div class='col-sm-3 bold'>" +
                "Email" +
                "</div>" +
                //"<div class='col-sm-2 bold'>" +
                //"Password" +
                //"</div>" +
                "<div class='col-sm-1'></div></div>";

    }
    for (var i = 2; i < csv_data.length - 1; i++) {
        Nric = csv_data[i][0];
        Name = csv_data[i][1];
        Phone = csv_data[i][2];
        Address = csv_data[i][3];
        Birth_date = csv_data[i][4];
        Gender = csv_data[i][5];
        Email = csv_data[i][6];
        Hourly_Rate = csv_data[i][7];

        html += "<div class='row' rel='" + i + "' id='row_con_" + i + "'>" +
                "<div class='col-sm-1 bold'></div>" +
                "<div class='col-sm-2'>" +
                "<input type='text' name='con_username[]' id='con_username_" + i + "' class='form-control' value='" + Name + "' readonly='readonly'>" +
                "<input type='hidden' name='con_nric[]' id='con_nric_" + i + "' class='form-control' value = '" + Nric + "'>" +
                "<input type='hidden' name='con_addresses[]' id='con_addresses_" + i + "' class='form-control' value = '" + Address + "'>" +
                //"<input type='hidden' name='con_images[]' id='con_images_" + i + "' class='form-control' value = '" + Image + "'>" +
                "<input type='hidden' name='con_birthdates[]' id='con_birthdates_num_" + i + "' class='form-control' value = '" + Birth_date + "'>" +
                "<input type='hidden' name='con_genders[]' id='con_genders_num_" + i + "' class='form-control' value = '" + Gender + "'>" +
                "<input type='hidden' name='con_rate[]' id='con_rate_" + i + "' class='form-control' value = '" + Hourly_Rate + "'>" +
                "</div>" +
                "<div class='col-sm-3'>" +
                "<input type='text' name='con_phones[]' id='con_phones_num_" + i + "' class='form-control' value='" + Phone + "' readonly='readonly'>" +
                "</div>" +
                "<div class='col-sm-3'>" +
                "<input type='text' name='con_emails[]' id='con_emails_num_" + i + "' class='form-control' value='" + Email + "' readonly='readonly'>" +
                "</div>" +
                //"<div class='col-sm-2'>" +
                //"<input type='text' name='con_pwd[]' id='con_pwd_num_" + i + "' class='form-control' value='" + generatePassword(16) + "' readonly='readonly'>" +
                //"</div>" +
                "<div class='col-sm-1'></div></div><br/>";



    }

    html += "<br/><br/><div class='col-sm-4'></div><div class='container-login100-form-btn col-sm-3'>" +
            "<div class='wrap-login100-form-btn'>" +
            "<div class='login100-form-bgbtn'></div>" +
            "<button class='login100-form-btn' type='submit'>" +
            "Upload Tutor" +
            "</button>" +
            "</div>" +
            "</div>";

    $("#" + append_container).append(html);
    $("#upload_container").remove();
    $("#upload_container").empty();
    $(error_el).html("");

}

function processCSVStudentData(csv_data, append_container, error_el, branch_id) {
    console.log(branch_id);
    var html = "";
    if (csv_data.length > 0) {
        html += "<div class='row'>" +
                "<div class='col-sm-1'></div>" +
                "<div class='col-sm-2 bold'>" +
                "Student Name" +
                "</div>" +
                "<div class='col-sm-2 bold'>" +
                "Academic Level" +
                "</div>" +
                "<div class='col-sm-2 bold'>" +
                "Parent Name" +
                "</div>" +
                "<div class='col-sm-2 bold'>" +
                "Parent Mobile" +
                "</div>" +
                "<div class='col-sm-1'></div></div>";

    }
    for (var i = 2; i < csv_data.length - 1; i++) {
        Nric = csv_data[i][0];
        Name = csv_data[i][1];
        Phone = csv_data[i][2];
        Address = csv_data[i][3];
        Birth_date = csv_data[i][4];
        Gender = csv_data[i][5];
        Email = csv_data[i][6];
        School = csv_data[i][7];
        Academic_level = csv_data[i][8];
        Parent_name = csv_data[i][9];
        Relationship = csv_data[i][10];
        Parent_nationality = csv_data[i][11];
        var Parent_company = "";
        if(csv_data[i][12] != ""){
            Parent_company = csv_data[i][12];
        }
        Parent_designation = csv_data[i][13];
        Parent_mobile = csv_data[i][14];
        Parent_email = csv_data[i][15];
        Registration_fee = csv_data[i][16];
        Outstanding_Registration_fee = csv_data[i][17];
        Student_status = csv_data[i][18];

        html += "<div class='row' rel='" + i + "' id='row_con_" + i + "'>" +
                "<div class='col-sm-1 bold'></div>" +
                "<div class='col-sm-2'>" +
                "<input type='hidden' name='con_nric[]' id='con_nric_" + i + "' class='form-control' value = '" + Nric + "'>" +
                "<input type='text' name='con_name[]' id='con_name_" + i + "' class='form-control' value='" + Name + "' readonly='readonly'>" +
                "</div>" +
                "<div class='col-sm-2'>" +
                "<input type='hidden' name='con_phones[]' id='con_phones_num_" + i + "' class='form-control' value = '" + Phone + "'>" +
                "<input type='hidden' name='con_addresses[]' id='con_addresses_" + i + "' class='form-control' value = '" + Address + "'>" +
                "<input type='hidden' name='con_birthdates[]' id='con_birthdates_num_" + i + "' class='form-control' value = '" + Birth_date + "'>" +
                "<input type='hidden' name='con_genders[]' id='con_genders_num_" + i + "' class='form-control' value='" + Gender + "'>" +
                "<input type='hidden' name='con_emails[]' id='con_emails_num_" + i + "' class='form-control' value='" + Email + "'>" +
                "<input type='hidden' name='con_school[]' id='con_school_num_" + i + "' class='form-control' value='" + School + "'>" +
                "<input type='hidden' name='con_relationship[]' id='con_relationship_num_" + i + "' class='form-control' value='" + Relationship + "'>" +
                "<input type='text' name='con_acadlevel[]' id='con_acadlevel_" + i + "' class='form-control' value='" + Academic_level + "' readonly='readonly'>" +
                "</div>" +
                "<div class='col-sm-2'>" +
                "<input type='hidden'' name='con_pwd[]' id='con_pwd_num_" + i + "' class='form-control' value='" + generatePassword(16) + "'>" +
                "<input type='text' name='con_parentName[]' id='con_parentName_" + i + "' class='form-control' value='" + Parent_name + "' readonly='readonly'>" +
                "</div>" +
                "<div class='col-sm-2'>" +
                "<input type='hidden' name='con_nationality[]' id='con_nationality_" + i + "' class='form-control' value='" + Parent_nationality + "'>" +
                "<input type='hidden' name='con_company[]' id='con_company_" + i + "' class='form-control' value='" + Parent_company + "'>" +
                "<input type='hidden' name='con_designation[]' id='con_designation_" + i + "' class='form-control' value='" + Parent_designation + "'>" +
                "<input type='text' name='con_mobile[]' id='con_mobile_num_" + i + "' class='form-control' value='" + Parent_mobile + "' readonly='readonly'>" +
                "</div>" +
                "<div class='col-sm-2'>" +
                "<input type='hidden' name='con_parentEmail[]' id='con_parentEmail_" + i + "' class='form-control' value='" + Parent_email + "'>" +
                "<input type='hidden' name='con_registrationFee[]' id='con_registrationFee_" + i + "' class='form-control' value='" + Registration_fee + "'>" +
                "<input type='hidden' name='con_outstandingRegistrationFee[]' id='con_outstandingRegistrationFee_" + i + "' class='form-control' value='" + Outstanding_Registration_fee + "'>" +
                "<input type='hidden' name='con_studentStatus[]' id='con_studentStatus_" + i + "' class='form-control' value='" + Student_status + "'>" +
                "</div>" +
                "<div class='col-sm-1'></div></div><br/>";



    }

    html += "<br/><br/><div class='col-sm-4'></div><div class='container-login100-form-btn col-sm-3'>" +
            "<div class='wrap-login100-form-btn'>" +
            "<div class='login100-form-bgbtn'></div>" +
            "<button class='login100-form-btn' type='submit'>" +
            "Upload Student" +
            "</button>" +
            "</div>" +
            "</div>";

    $("#" + append_container).append(html);
    $("#upload_container").remove();
    $("#upload_container").empty();
    $(error_el).html("");

}


