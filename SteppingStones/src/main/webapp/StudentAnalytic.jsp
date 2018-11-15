<%@page import="java.util.List"%>
<%@page import="entity.Branch"%>
<%@page import="model.BranchDAO"%>
<%@include file="protect_branch_admin.jsp"%>
<%@include file="header.jsp"%>
<div class="col-md-10">
    <!--<div style="text-align: center;margin: 20px;"><span class="tab_active">Add Tutor </span> / <a href="UploadTutor.jsp">Upload Tutors</a></h5></div>-->
    
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script src="https://code.highcharts.com/modules/export-data.js"></script>
    <div class="row">
        <div class="col-md-3"></div>
        <script src="https://code.highcharts.com/modules/data.js"></script>
        <script src="https://code.highcharts.com/modules/drilldown.js"></script>

        <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
        <div class="col-md-7"></div>
    </div>
</div>
    
</div>
</div>

<%@include file="footer.jsp"%>
<script>
    
Highcharts.chart('container', {
   chart: {
            type: 'column'
        },
        title: {
            text: 'Grade Anlytic By Primary 1 (School)'
        },
        xAxis: {
            type: 'category'
        },

        legend: {
            enabled: true
        },

        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    style: {
                        color: 'white',
                        textShadow: '0 0 2px black, 0 0 2px black'
                    }
                },
                stacking: 'normal'
            }
        },

        series: [{
            name: 'A range',
            data: [{
                name: 'English',
                y: 5,
                drilldown: 'eng'
            }, {
                name: 'Maths',
                y: 2,
                drilldown: 'math'
            }, {
                name: 'Science',
                y: 3,
                drilldown: 'science'
            }]
        }, {
            name: 'B range',
            data: [{
                name: 'English',
                y: 5,
                drilldown: 'eng2'
            }, {
                name: 'Maths',
                y: 5,
                drilldown: 'math2'
            }, {
                name: 'Science',
                y: 2,
                drilldown: 'science2'
            }]
        },
        {
            name: 'C range',
            data: [{
                name: 'English',
                y: 3,
                drilldown: 'eng3'
            }, {
                name: 'Maths',
                y: 2,
                drilldown: 'math3'
            }, {
                name: 'Science',
                y: 3,
                drilldown: 'science3'
            }]
        }
    ],
        drilldown: {
            activeDataLabelStyle: {
                color: 'white',
                textShadow: '0 0 2px black, 0 0 2px black'
            },
            series: [{
                id: 'eng',
                name: 'English',
                data: [
                    ['Norman', 90],
                    ['Maheswarn', 100],
                    ['Jannel', 89],
                    ['Linette', 87],
                    ['Syed', 99]
                ]
            }, {
                id: 'math',
                name: 'Maths',
                data: [
                    ['Linette', 88],
                    ['Syed', 91]
                ]
            }, {
                id: 'science',
                name: 'Science',
                data: [
                    ['Norman', 91],
                    ['Syed', 89],
                    ['Jannel', 93]
                ]
            },{
                id: 'eng2',
                name: 'English',
                data: [
                    ['Elizabeth',76],
                    ['Tang Jia', 73],
                    ['James Tan', 78],
                    ['Maheswaran', 73],
                    ['Sherie Oon', 74]
                ]
            }, {
                id: 'math2',
                name: 'Maths',
                data: [
                    ['Norman', 79],
                    ['James', 75],
                    ['James Tan', 78],
                    ['Maheswaran', 73],
                    ['Sherie Oon', 74]
                ]
            }, {
                id: 'science2',
                name: 'Science',
                data: [
                    ['Elizabeth', 72],
                    ['Syed Shakik', 69]
                ]
            },{
                id: 'eng3',
                name: 'English',
                data: [
                    ['Hui Ning',67],
                    ['Chan Yan', 63],
                    ['Clarence', 61]
                ]
            }, {
                id: 'maths2',
                name: 'Maths',
                data: [
                    ['Norman', 59],
                    ['James', 56]
                ]
            }, {
                id: 'science2',
                name: 'Science',
                data: [
                    ['Elizabeth', 65],
                    ['Syed Shakik', 69]
                ]
            }
        ]
        }
});
</script>