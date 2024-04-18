document.addEventListener('DOMContentLoaded', function(){
    new DataTable('#bordertable',{
        // 표시 건수기능 숨기기
        lengthChange: false,
        // 정보 표시 숨기기
        info: false,
        lengthMenu: [ 10, 20, 30, 40, 50 ], // -셋트1(같이사용해야 기능이 사용됩니다.)
        // 기본 표시 건수를 20건으로 설정
        displayLength: 20,                // -셋트1(같이사용해야 기능이 사용됩니다.)

        // 검색 기능 숨기기
        searching: true,


        columnDefs: [
            // 1번재 항목 열을 숨김
            { targets: 0, width: 70 },

            // 2번째 항목의 넓이를 100px로 설정
            { targets: 3, width: 100 }

        ],
        //반응형 테이블 설정
        responsive: true

    });

    //윈도우 크기를 조절하면 자동으로 새로고침 해주는 스크립트
    window.onresize = function(){
        document.location.reload();
    };
});