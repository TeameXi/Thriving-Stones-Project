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


function checkCSVExtension(file_upload_id,error_lbl_id,append_container,option){
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
          var header = data[0];
          
            if(option === 'tutor'){
                if(header.length === 6 && header[0] === "Tutor NRIC" && header[1] === "Name" && header[2] === "Phone" 
                && header[3] === "Email" && header[4] === "Age" 
                && header[5] === "Gender"){
                    $("#"+append_container).html("");
                    processCSVTutorData(data,append_container,error_el);
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


function processCSVTutorData(csv_data,append_container,error_el) {
    var html = "";
    if(csv_data.length > 0){
        html += "<div class='row'>" +   
        "<div class='col-sm-2 bold'>"+ 
          "Tutor Name"+
        "</div>" + 
        "<div class='col-sm-2 bold'>"+
          "Phone"+
        "</div>" + 
        "<div class='col-sm-3 bold'>"+
          "Email"+
        "</div>" + 
        "<div class='col-sm-1 bold'>"+
          "Age"+
        "</div>" + 
        "<div class='col-sm-1 bold'>"+
          "Gender"+
        "</div>"+
        "<div class='col-sm-2 bold'>"+
          "Password"+
        "</div>";
    }
    for(var i = 1; i < csv_data.length-1; i++) {
        Nric = csv_data[i][0];
        Name = csv_data[i][1];
        Phone = csv_data[i][2];
        Email = csv_data[i][3];
        Age = csv_data[i][4];
        Gender = csv_data[i][5];
        
        html += "<div class='row container' rel='"+i+"' id='row_con_" + i + "'>" +   
                            "<div class='col-sm-2'>"+ 
                              "<input type='text' name='con_username[]' id='con_username_" + i + "' class='form-control' value='" + Name + "' readonly='readonly'>"+
                              "<input type='hidden' name='con_id[]' id='con_id_" + i + "' class='form-control' value = '" + Nric + "'>"+
                            "</div>" + 
                            "<div class='col-sm-2'>"+
                              "<input type='text' name='con_phones[]' id='con_phones_num_" + i + "' class='form-control' value='" + Phone + "' readonly='readonly'>"+
                            "</div>" + 
                            "<div class='col-sm-3'>"+
                              "<input type='text' name='con_emails[]' id='con_emails_num_" + i + "' class='form-control' value='" +Email + "' readonly='readonly'>"+
                            "</div>" + 
                            "<div class='col-sm-1'>"+
                              "<input type='text' name='con_ages_num[]' id='con_ages_num_" + i + "' class='form-control' value='" + Age + "' readonly='readonly'>"+
                            "</div>" + 
                            "<div class='col-sm-1'>"+
                              "<input type='text' name='con_gender_num[]' id='con_gender_num_" + i + "' class='form-control' value='" + Gender + "' readonly='readonly'>"+
                            "</div>"+
                            "<div class='col-sm-2'>"+
                              "<input type='text' name='con_pwd_num[]' id='con_pwd_num_" + i + "' class='form-control' value='" + generatePassword(16) + "' readonly='readonly'>"+
                            "</div>"+
                            "</div>";
             
      
    }  
    
    html += "<br/><div class='container-login100-form-btn col-sm-3'>"+
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



