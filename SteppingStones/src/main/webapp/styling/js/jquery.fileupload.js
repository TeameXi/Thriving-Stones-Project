function readCSVFile(csv, delimeter = ";") {
    var allTextLines = csv.split(/\r\n|\n/);
    var lines = [];
    for (var i=0; i<allTextLines.length; i++) {
        var data = allTextLines[i].split(delimeter);
            var tarr = [];
            for (var j=0; j<data.length; j++) {
                tarr.push(data[j]);
            }
            lines.push(tarr);
    }
    return lines;
}


function checkCSVExtension(file_upload_id,error_lbl_id,append_container,option,branch_id){
  var file_upload_el = $("#"+file_upload_id);
  var error_el = $("#"+error_lbl_id);
  var file_name = file_upload_el.prop("files")[0]["name"];
  var file_extension = file_name.split('.').pop();

  if(file_extension !== "csv") {
      $(error_el).html("<span style='color:red;'>Please insert a valid csv file!</span>");
  } else {
      // Read file contents
      $(error_el).html("<span style='color:green;'>Reading file contents...</span>");
      var reader = new FileReader();
      reader.readAsText(file_upload_el.prop("files")[0]);
      reader.onload = function(event) {
          var csv = event.target.result;
          var data = readCSVFile(csv,",");

          // validate template format
          var header = data[1];
   
            if(option === 'tutor'){
                if(header.length === 8 && header[0] === "Tutor NRIC" && header[1] === "Full Name" 
                && header[2] === "Phone" && header[3] === "Address"
                && header[4]=== "Image"  && header[5] === "Birth Date (DD-MM-YYYY)" 
                && header[6] === "Gender (F/M)" && header[7] === "Email"){
                    $("#"+append_container).html("");
                    processCSVTutorData(data,append_container,error_el,branch_id);
                }else{
                    $(error_el).html("<span style='color:red;'>Invalid Template</span>");
                }
            }else{
                if(header.length === 6 && header[0] === "Tutor NRIC" && header[1] === "Name" && header[2] === "Phone" 
                && header[3] === "Email" && header[4] === "Age" 
                && header[5] === "Gender"){
                    $("#"+append_container).html("");
                    processCSVTutorData(data,append_container,error_el);
                }else{
                    $(error_el).html("<span style='color:red;'>Invalid Template</span>");
                }
            }
      };
      $(file_upload_el).val("");
  } 
}


function processCSVTutorData(csv_data,append_container,error_el,branch_id) {
    console.log(branch_id);
    var html = "";
    if(csv_data.length > 0){
        html += "<div class='row'>" + 
        "<div class='col-sm-1'></div>"+
        "<div class='col-sm-2 bold'>"+ 
          "Tutor Name"+
        "</div>" + 
        "<div class='col-sm-3 bold'>"+
          "Phone"+
        "</div>" + 
        "<div class='col-sm-3 bold'>"+
          "Email"+
        "</div>"+
        "<div class='col-sm-2 bold'>"+
          "Password"+
        "</div>"+
        "<div class='col-sm-1'></div></div>";

    }
    for(var i = 2; i < csv_data.length-1; i++) {
        Nric = csv_data[i][0];
        Name = csv_data[i][1];
        Phone = csv_data[i][2];
        Address = csv_data[i][3];
        Image = csv_data[i][4];
        Birth_date = csv_data[i][5];
        Gender = csv_data[i][6];
        Email = csv_data[i][7];      
        
        html += "<div class='row' rel='"+i+"' id='row_con_" + i + "'>" +
                    "<div class='col-sm-1 bold'></div>"+
                    "<div class='col-sm-2'>"+ 
                      "<input type='text' name='con_username[]' id='con_username_" + i + "' class='form-control' value='" + Name + "' readonly='readonly'>"+
                      "<input type='hidden' name='con_nric[]' id='con_nric_" + i + "' class='form-control' value = '" + Nric + "'>"+
                      "<input type='hidden' name='con_addresses[]' id='con_addresses_" + i + "' class='form-control' value = '" + Address + "'>"+
                      "<input type='hidden' name='con_images[]' id='con_images_" + i + "' class='form-control' value = '" + Image + "'>"+
                      "<input type='hidden' name='con_birthdates[]' id='con_birthdates_num_" + i + "' class='form-control' value = '" + Birth_date + "'>"+
                      "<input type='hidden' name='con_genders[]' id='con_genders_num_" + i + "' class='form-control' value = '" + Gender + "'>"+
                    "</div>" + 
                    "<div class='col-sm-3'>"+
                      "<input type='text' name='con_phones[]' id='con_phones_num_" + i + "' class='form-control' value='" + Phone + "' readonly='readonly'>"+
                    "</div>" + 
                    "<div class='col-sm-3'>"+
                      "<input type='text' name='con_emails[]' id='con_emails_num_" + i + "' class='form-control' value='" +Email + "' readonly='readonly'>"+
                    "</div>" +
                    "<div class='col-sm-2'>"+
                      "<input type='text' name='con_pwd[]' id='con_pwd_num_" + i + "' class='form-control' value='" + generatePassword(16) + "' readonly='readonly'>"+
                    "</div>"+
                    "<div class='col-sm-1'></div></div><br/>";
                    
             
      
    }  
    
    html += "<br/><br/><div class='col-sm-4'></div><div class='container-login100-form-btn col-sm-3'>"+
                    "<div class='wrap-login100-form-btn'>"+
                        "<div class='login100-form-bgbtn'></div>"+
                        "<button class='login100-form-btn' type='submit'>"+
                            "Upload Tutor"+
                        "</button>"+
                    "</div>"+
                "</div>";
   
    $("#"+append_container).append(html);
    $("#upload_container").remove();
    $("#upload_container").empty();
    $(error_el).html("");
    
}



