<%@page import="java.util.ArrayList"%>
<%@page import="model.LevelDAO"%>
<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<style>
    #filter_container{
        position: block;
        background:#666;
        padding:20px;
    }
</style>


<div class="col-md-10" id="filter_container">
    <form class="form-inline">
        <div class="col-md-2"></div>
        <div class="form-group col-md-4">
            <label style="color:white">Level : </label>

                <select name="levelID" class="form-control levelSelect" id="levelID">
                    <option value="">Select Level</option>
                    <%  
                        LevelDAO levels = new LevelDAO();
                        ArrayList<String> levelList = levels.retrieveAllLevels();

                        for (String level : levelList) {
                            out.println("<option value='" + LevelDAO.retrieveLevelID(level) + "'>" + level + "</option>");
                        }
                    %>
                </select>
             
        </div>
        <div class="form-group col-md-6">
            <label style="color:white">Subject :</label>
            <select class="form-control" id="subject">
                <option value="0">Select Level first</option>
            </select>
        </div>
       
    </form>
</div>
<br/><br/>
<div class="col-md-10">
    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</div>

<div class="col-md-10">
    <div id="container2" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</div>
    
</div>
</div>

<%@include file="footer.jsp"%>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="http://github.highcharts.com/master/modules/drilldown.js"></script>

<script>
    $(".levelSelect").change(function () {
        action = 'retrieveSubjectOptions';
        levelID = $(".levelSelect").val();
        
        $.ajax({
            type: 'POST',
            url: 'AdminScheduleServlet',
            dataType: 'JSON',
            data: {levelID: levelID, action: action},
            success: function (data) {
                $("#subject").empty();
                $("#container").empty();
                $("#assign_tutor").empty();
                $("#subject").append('<option value="0">Select Subject</option>');
                for (var i = 0; i < data.subject.length; i++) {
                    $("#subject").append('<option value="' + data.subject[i].id + '">' + data.subject[i].name + '</option>');
                }
            }
        });
    });
    
    $("#subject").change(function () {
        var subSelect = $("#subject").val();
       
        if(subSelect.length !== 0){
            var lvlName = $(".levelSelect option:selected").html();
            var subName = $("#subject option:selected").html();
            $(function () {
                // Create the chart
                $('#container').highcharts({
                    "title": {
                        text: 'Center Grade for '+subName+"-"+lvlName +"[SChool]"
                    },
                    "subtitle": {
                        text: 'Can be further breakdown by clicking on each bar'
                    },
                    "chart": {
                      "type": "column"
                    },
                    "credits": false,
                    "legend": {
                      "enabled": true,
                      "layout": "vertical",
                      "align": "right",
                      "verticalAlign": "middle",
                      "borderWidth": 0
                    },
                    "xAxis": {
                      "labels": {
                        "rotation": -90
                      },
                      "type": "category"
                    },
                    "yAxis": {
                      "min": 0,
                      "title": {
                        "text": ""
                      }
                    },
                    "plotOptions": {
                      "series": {
                        "animation": false,
                        "showInLegend": true,
                        "dataLabels": {
                          "enabled": false,
                          "color": "grey",
                          "style": {
                            "text-shadow": "0 0 2px black"
                          }
                        },
                        "stacking": null
                      }
                    },
                    "series": [
                      {
                        "name": "Grade A",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 2,
                            "drilldown": "0|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 3,
                            "drilldown": "0|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 4,
                            "drilldown": "0|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 2,
                            "drilldown": "0|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade B",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 4,
                            "drilldown": "1|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 3,
                            "drilldown": "1|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 5,
                            "drilldown": "1|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 3,
                            "drilldown": "1|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade C",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 3,
                            "drilldown": "3|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 2,
                            "drilldown": "3|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 1,
                            "drilldown": "3|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 4,
                            "drilldown": "3|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade D",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 2,
                            "drilldown": "4|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 4,
                            "drilldown": "4|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 3,
                            "drilldown": "4|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 1,
                            "drilldown": "4|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade E",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 5,
                            "drilldown": "5|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 1,
                            "drilldown": "5|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 4,
                            "drilldown": "5|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 2,
                            "drilldown": "5|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade F",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 2,
                            "drilldown": "6|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 5,
                            "drilldown": "6|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 0,
                            "drilldown": "6|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 3,
                            "drilldown": "6|SA2"
                          }
                        ]
                      }
//                      ,
//                      {
//                        type: 'spline',
//                        name: 'Average',
//                        data: [3, 2.67, 3, 6.33],
//                        marker: {
//                            lineWidth: 2,
//                            lineColor: Highcharts.getOptions().colors[3],
//                            fillColor: 'white'
//                        }
//                       }
                    ],
                    "drilldown": {
                      "series": [
                        // Grade A
                        {
                          "id": "0|CA1",
                          "name": [
                            "Grade A students for CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              87
                            ],
                            [
                              "Jannel koh",
                              83
                            ]
                          ]
                        },
                        {
                          "id": "0|SA1",
                          "name": [
                            "Grade A students for SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              91
                            ],
                            [
                              "Jannel koh",
                              89
                            ],
                            [
                              "Noman Leow Teck",
                              87
                            ]
                          ]
                        },
                        {
                          "id": "0|CA2",
                          "name": [
                            "Grade A students for CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Jannel koh",
                              93
                            ],
                            [
                              "James Tan",
                              92
                            ],
                            [
                              "Norman Leow Teck",
                              89
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              86
                            ]
                          ]
                        },
                        {
                          "id": "0|SA2",
                          "name": [
                            "Grade A students for SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              88
                            ],
                            [
                              "Jannel Koh",
                              86
                            ]
                          ]
                        },

                        // Grade B
                        {
                          "id": "1|CA1",
                          "name": [
                            "Grade B Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Syed Shaik Aifa",
                              78
                            ],
                            [
                              "Tang Jia Heng",
                              76
                            ],
                            [
                              "Sheerie Oon",
                              75
                            ],
                            [
                              "Carrene Tan",
                              75
                            ]
                          ]
                        },
                        {
                          "id": "1|SA1",
                          "name": [
                            "Grade B Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              79
                            ],
                            [
                              "Sherie Oo",
                              78
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              77
                            ]
                          ]
                        },
                        {
                          "id": "1|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elizabeth Joy Nooh",
                              79
                            ],
                            [
                              "Linette Evelyn Natalrary",
                              77
                            ],
                            [
                              "Carrene Tan",
                              75
                            ],
                            [
                              "RU",
                              67276
                            ]
                          ]
                        },
                        {
                          "id": "1|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              77
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              75
                            ],
                            [
                              "SK",
                              291
                            ],
                            [
                              "PDC",
                              278
                            ],
                            [
                              "BG",
                              254
                            ],
                            [
                              "RO",
                              212
                            ],
                            [
                              "AR",
                              176
                            ],
                            [
                              "CY",
                              112
                            ],
                            [
                              "IN",
                              24
                            ],
                            [
                              "Jannel koh",
                              14
                            ],
                            [
                              "MT",
                              14
                            ]
                          ]
                        },

                        // Grade C (across exam)
                        {
                          "id": "3|CA1",
                          "name": [
                            "Grade C Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Melody Tan",
                              63
                            ],
                            [
                              "Eng Ting Xuan",
                              62
                            ],
                            [
                              "Chan Yan Wees",
                              60
                            ]
                          ]
                        },
                        {
                          "id": "3|SA1",
                          "name": [
                            "Grade C Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              79
                            ],
                            [
                              "Sherie Oo",
                              78
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              77
                            ]
                          ]
                        },
                        {
                          "id": "3|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elizabeth Joy Nooh",
                              79
                            ],
                            [
                              "Linette Evelyn Natalrary",
                              77
                            ],
                            [
                              "Carrene Tan",
                              75
                            ],
                            [
                              "RU",
                              67276
                            ]
                          ]
                        },
                        {
                          "id": "3|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              77
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              75
                            ],
                            [
                              "SK",
                              291
                            ],
                            [
                              "PDC",
                              278
                            ],
                            [
                              "BG",
                              254
                            ],
                            [
                              "RO",
                              212
                            ],
                            [
                              "AR",
                              176
                            ],
                            [
                              "CY",
                              112
                            ],
                            [
                              "IN",
                              24
                            ],
                            [
                              "Jannel koh",
                              14
                            ],
                            [
                              "MT",
                              14
                            ]
                          ]
                        },

                        // Grade D (across exam)
                        {
                          "id": "4|CA1",
                          "name": [
                            "Grade D Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Ko Hui Ning",
                              59
                            ],
                            [
                              "Clarence Lim",
                              59
                            ]
                          ]
                        },
                        {
                          "id": "4|SA1",
                          "name": [
                            "Grade C Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              79
                            ],
                            [
                              "Sherie Oo",
                              78
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              77
                            ]
                          ]
                        },
                        {
                          "id": "4|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elizabeth Joy Nooh",
                              79
                            ],
                            [
                              "Linette Evelyn Natalrary",
                              77
                            ],
                            [
                              "Carrene Tan",
                              75
                            ],
                            [
                              "RU",
                              67276
                            ]
                          ]
                        },
                        {
                          "id": "4|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              77
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              75
                            ],
                            [
                              "SK",
                              291
                            ],
                            [
                              "PDC",
                              278
                            ],
                            [
                              "BG",
                              254
                            ],
                            [
                              "RO",
                              212
                            ],
                            [
                              "AR",
                              176
                            ],
                            [
                              "CY",
                              112
                            ],
                            [
                              "IN",
                              24
                            ],
                            [
                              "Jannel koh",
                              14
                            ],
                            [
                              "MT",
                              14
                            ]
                          ]
                        },

                        //Grade E (across exam)
                        {
                          "id": "5|CA1",
                          "name": [
                            "Grade E Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Lee Han Xun",
                              55
                            ],
                            [
                              "Aden Goh Ye Da",
                              53
                            ],
                            [
                              "Alton Lim Kai Jie",
                              51
                            ],
                            [
                              "Soh Pi Hoon",
                              50
                            ],
                            [
                              "Anne Clare",
                              50
                            ]
                          ]
                        },
                        {
                          "id": "5|SA1",
                          "name": [
                            "Grade C Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              79
                            ],
                            [
                              "Sherie Oo",
                              78
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              77
                            ]
                          ]
                        },
                        {
                          "id": "5|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elizabeth Joy Nooh",
                              79
                            ],
                            [
                              "Linette Evelyn Natalrary",
                              77
                            ],
                            [
                              "Carrene Tan",
                              75
                            ],
                            [
                              "RU",
                              67276
                            ]
                          ]
                        },
                        {
                          "id": "5|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              77
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              75
                            ],
                            [
                              "SK",
                              291
                            ],
                            [
                              "PDC",
                              278
                            ],
                            [
                              "BG",
                              254
                            ],
                            [
                              "RO",
                              212
                            ],
                            [
                              "AR",
                              176
                            ],
                            [
                              "CY",
                              112
                            ],
                            [
                              "IN",
                              24
                            ],
                            [
                              "Jannel koh",
                              14
                            ],
                            [
                              "MT",
                              14
                            ]
                          ]
                        },

                        // Grade F (across exam)
                        {
                          "id": "6|CA1",
                          "name": [
                            "Grade F Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Pek Min Xi",
                              28
                            ],
                            [
                              "Sajith Banhu",
                              21
                            ]
                          ]
                        },
                        {
                          "id": "6|SA1",
                          "name": [
                            "Grade C Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              79
                            ],
                            [
                              "Sherie Oo",
                              78
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              77
                            ]
                          ]
                        },
                        {
                          "id": "6|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elizabeth Joy Nooh",
                              79
                            ],
                            [
                              "Linette Evelyn Natalrary",
                              77
                            ],
                            [
                              "Carrene Tan",
                              75
                            ],
                            [
                              "RU",
                              67276
                            ]
                          ]
                        },
                        {
                          "id": "6|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              77
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              75
                            ],
                            [
                              "SK",
                              291
                            ],
                            [
                              "PDC",
                              278
                            ],
                            [
                              "BG",
                              254
                            ],
                            [
                              "RO",
                              212
                            ],
                            [
                              "AR",
                              176
                            ],
                            [
                              "CY",
                              112
                            ],
                            [
                              "IN",
                              24
                            ],
                            [
                              "Jannel koh",
                              14
                            ],
                            [
                              "MT",
                              14
                            ]
                          ]
                        }

                      ]
                    }
                });
                
                $('#container2').highcharts({
                    "title": {
                        text: 'Grade for '+subName+"-"+lvlName +"[School]"
                    },
                    "subtitle": {
                        text: 'Can be further breakdown by clicking on each bar'
                    },
                    "chart": {
                      "type": "column"
                    },
                    "credits": false,
                    "legend": {
                      "enabled": true,
                      "layout": "vertical",
                      "align": "right",
                      "verticalAlign": "middle",
                      "borderWidth": 0
                    },
                    "xAxis": {
                      "labels": {
                        "rotation": -90
                      },
                      "type": "category"
                    },
                    "yAxis": {
                      "min": 0,
                      "title": {
                        "text": ""
                      }
                    },
                    "plotOptions": {
                      "series": {
                        "animation": false,
                        "showInLegend": true,
                        "dataLabels": {
                          "enabled": false,
                          "color": "grey",
                          "style": {
                            "text-shadow": "0 0 2px black"
                          }
                        },
                        "stacking": null
                      }
                    },
                    "series": [
                      {
                        "name": "Grade A",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 2,
                            "drilldown": "0|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 3,
                            "drilldown": "0|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 4,
                            "drilldown": "0|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 2,
                            "drilldown": "0|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade B",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 4,
                            "drilldown": "1|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 3,
                            "drilldown": "1|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 5,
                            "drilldown": "1|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 3,
                            "drilldown": "1|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade C",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 3,
                            "drilldown": "3|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 2,
                            "drilldown": "3|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 1,
                            "drilldown": "3|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 4,
                            "drilldown": "3|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade D",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 2,
                            "drilldown": "4|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 4,
                            "drilldown": "4|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 3,
                            "drilldown": "4|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 1,
                            "drilldown": "4|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade E",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 5,
                            "drilldown": "5|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 1,
                            "drilldown": "5|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 4,
                            "drilldown": "5|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 2,
                            "drilldown": "5|SA2"
                          }
                        ]
                      },
                      {
                        "name": "Grade F",
                        "cropThreshold": 500,
                        "type": "column",
                        "data": [
                          {
                            "name": "CA1",
                            "y": 2,
                            "drilldown": "6|CA1"
                          },
                          {
                            "name": "SA1",
                            "y": 5,
                            "drilldown": "6|SA1"
                          },
                          {
                            "name": "CA2",
                            "y": 0,
                            "drilldown": "6|CA2"
                          },
                          {
                            "name": "SA2",
                            "y": 3,
                            "drilldown": "6|SA2"
                          }
                        ]
                      }
//                      ,
//                      {
//                        type: 'spline',
//                        name: 'Average',
//                        data: [3, 2.67, 3, 6.33],
//                        marker: {
//                            lineWidth: 2,
//                            lineColor: Highcharts.getOptions().colors[3],
//                            fillColor: 'white'
//                        }
//                       }
                    ],
                    "drilldown": {
                      "series": [
                        // Grade A
                        {
                          "id": "0|CA1",
                          "name": [
                            "Grade A students for CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              87
                            ],
                            [
                              "Jannel koh",
                              83
                            ]
                          ]
                        },
                        {
                          "id": "0|SA1",
                          "name": [
                            "Grade A students for SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              91
                            ],
                            [
                              "Jannel koh",
                              89
                            ],
                            [
                              "Noman Leow Teck",
                              87
                            ]
                          ]
                        },
                        {
                          "id": "0|CA2",
                          "name": [
                            "Grade A students for CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Jannel koh",
                              93
                            ],
                            [
                              "James Tan",
                              92
                            ],
                            [
                              "Norman Leow Teck",
                              89
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              86
                            ]
                          ]
                        },
                        {
                          "id": "0|SA2",
                          "name": [
                            "Grade A students for SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              88
                            ],
                            [
                              "Jannel Koh",
                              86
                            ]
                          ]
                        },

                        // Grade B
                        {
                          "id": "1|CA1",
                          "name": [
                            "Grade B Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Syed Shaik Aifa",
                              78
                            ],
                            [
                              "Tang Jia Heng",
                              76
                            ],
                            [
                              "Sheerie Oon",
                              75
                            ],
                            [
                              "Carrene Tan",
                              75
                            ]
                          ]
                        },
                        {
                          "id": "1|SA1",
                          "name": [
                            "Grade B Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              79
                            ],
                            [
                              "Sherie Oo",
                              78
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              77
                            ]
                          ]
                        },
                        {
                          "id": "1|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elizabeth Joy Nooh",
                              79
                            ],
                            [
                              "Linette Evelyn Natalrary",
                              77
                            ],
                            [
                              "Carrene Tan",
                              75
                            ],
                            [
                              "RU",
                              67276
                            ]
                          ]
                        },
                        {
                          "id": "1|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              77
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              75
                            ],
                            [
                              "SK",
                              291
                            ],
                            [
                              "PDC",
                              278
                            ],
                            [
                              "BG",
                              254
                            ],
                            [
                              "RO",
                              212
                            ],
                            [
                              "AR",
                              176
                            ],
                            [
                              "CY",
                              112
                            ],
                            [
                              "IN",
                              24
                            ],
                            [
                              "Jannel koh",
                              14
                            ],
                            [
                              "MT",
                              14
                            ]
                          ]
                        },

                        // Grade C (across exam)
                        {
                          "id": "3|CA1",
                          "name": [
                            "Grade C Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Melody Tan",
                              63
                            ],
                            [
                              "Eng Ting Xuan",
                              62
                            ],
                            [
                              "Chan Yan Wees",
                              60
                            ]
                          ]
                        },
                        {
                          "id": "3|SA1",
                          "name": [
                            "Grade C Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              79
                            ],
                            [
                              "Sherie Oo",
                              78
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              77
                            ]
                          ]
                        },
                        {
                          "id": "3|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elizabeth Joy Nooh",
                              79
                            ],
                            [
                              "Linette Evelyn Natalrary",
                              77
                            ],
                            [
                              "Carrene Tan",
                              75
                            ],
                            [
                              "RU",
                              67276
                            ]
                          ]
                        },
                        {
                          "id": "3|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              77
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              75
                            ],
                            [
                              "SK",
                              291
                            ],
                            [
                              "PDC",
                              278
                            ],
                            [
                              "BG",
                              254
                            ],
                            [
                              "RO",
                              212
                            ],
                            [
                              "AR",
                              176
                            ],
                            [
                              "CY",
                              112
                            ],
                            [
                              "IN",
                              24
                            ],
                            [
                              "Jannel koh",
                              14
                            ],
                            [
                              "MT",
                              14
                            ]
                          ]
                        },

                        // Grade D (across exam)
                        {
                          "id": "4|CA1",
                          "name": [
                            "Grade D Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Ko Hui Ning",
                              59
                            ],
                            [
                              "Clarence Lim",
                              59
                            ]
                          ]
                        },
                        {
                          "id": "4|SA1",
                          "name": [
                            "Grade C Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              79
                            ],
                            [
                              "Sherie Oo",
                              78
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              77
                            ]
                          ]
                        },
                        {
                          "id": "4|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elizabeth Joy Nooh",
                              79
                            ],
                            [
                              "Linette Evelyn Natalrary",
                              77
                            ],
                            [
                              "Carrene Tan",
                              75
                            ],
                            [
                              "RU",
                              67276
                            ]
                          ]
                        },
                        {
                          "id": "4|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              77
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              75
                            ],
                            [
                              "SK",
                              291
                            ],
                            [
                              "PDC",
                              278
                            ],
                            [
                              "BG",
                              254
                            ],
                            [
                              "RO",
                              212
                            ],
                            [
                              "AR",
                              176
                            ],
                            [
                              "CY",
                              112
                            ],
                            [
                              "IN",
                              24
                            ],
                            [
                              "Jannel koh",
                              14
                            ],
                            [
                              "MT",
                              14
                            ]
                          ]
                        },

                        //Grade E (across exam)
                        {
                          "id": "5|CA1",
                          "name": [
                            "Grade E Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Lee Han Xun",
                              55
                            ],
                            [
                              "Aden Goh Ye Da",
                              53
                            ],
                            [
                              "Alton Lim Kai Jie",
                              51
                            ],
                            [
                              "Soh Pi Hoon",
                              50
                            ],
                            [
                              "Anne Clare",
                              50
                            ]
                          ]
                        },
                        {
                          "id": "5|SA1",
                          "name": [
                            "Grade C Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              79
                            ],
                            [
                              "Sherie Oo",
                              78
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              77
                            ]
                          ]
                        },
                        {
                          "id": "5|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elizabeth Joy Nooh",
                              79
                            ],
                            [
                              "Linette Evelyn Natalrary",
                              77
                            ],
                            [
                              "Carrene Tan",
                              75
                            ],
                            [
                              "RU",
                              67276
                            ]
                          ]
                        },
                        {
                          "id": "5|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              77
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              75
                            ],
                            [
                              "SK",
                              291
                            ],
                            [
                              "PDC",
                              278
                            ],
                            [
                              "BG",
                              254
                            ],
                            [
                              "RO",
                              212
                            ],
                            [
                              "AR",
                              176
                            ],
                            [
                              "CY",
                              112
                            ],
                            [
                              "IN",
                              24
                            ],
                            [
                              "Jannel koh",
                              14
                            ],
                            [
                              "MT",
                              14
                            ]
                          ]
                        },

                        // Grade F (across exam)
                        {
                          "id": "6|CA1",
                          "name": [
                            "Grade F Students For CA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Pek Min Xi",
                              28
                            ],
                            [
                              "Sajith Banhu",
                              21
                            ]
                          ]
                        },
                        {
                          "id": "6|SA1",
                          "name": [
                            "Grade C Students For SA1"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Norman Leow Teck",
                              79
                            ],
                            [
                              "Sherie Oo",
                              78
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              77
                            ]
                          ]
                        },
                        {
                          "id": "6|CA2",
                          "name": [
                            "Grade B Students For CA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "Elizabeth Joy Nooh",
                              79
                            ],
                            [
                              "Linette Evelyn Natalrary",
                              77
                            ],
                            [
                              "Carrene Tan",
                              75
                            ],
                            [
                              "RU",
                              67276
                            ]
                          ]
                        },
                        {
                          "id": "6|SA2",
                          "name": [
                            "Grade B Students For SA2"
                          ],
                          "type": "column",
                          "data": [
                            [
                              "James Tan",
                              77
                            ],
                            [
                              "Elizabeth Joy Nooh",
                              75
                            ],
                            [
                              "SK",
                              291
                            ],
                            [
                              "PDC",
                              278
                            ],
                            [
                              "BG",
                              254
                            ],
                            [
                              "RO",
                              212
                            ],
                            [
                              "AR",
                              176
                            ],
                            [
                              "CY",
                              112
                            ],
                            [
                              "IN",
                              24
                            ],
                            [
                              "Jannel koh",
                              14
                            ],
                            [
                              "MT",
                              14
                            ]
                          ]
                        }

                      ]
                    }
                });
            });
            
   
        }
    });
    

    
   
</script>