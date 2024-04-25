window.onload = function () {
    // 1차 옵션 선택 시
    document.getElementById('first-option', 'galaxy-options', 'iphone-options').addEventListener('change', function () {
        var selectedOption = this.value
        var iphoneOptions = document.getElementById('iphone-options');
        var galaxyOptions = document.getElementById('galaxy-options');
        var caseOptions = document.getElementById('case-options');


        // 아이폰을 선택한 경우
        if (selectedOption.toLowerCase() === '아이폰') {
            iphoneOptions.classList.remove('form-select2');
            galaxyOptions.classList.add('form-select2');
        }
        // 갤럭시를 선택한 경우
        else if (selectedOption.toLowerCase() === '갤럭시') {
            iphoneOptions.classList.add('form-select2');
            galaxyOptions.classList.remove('form-select2');
        }

        // 기본 선택(선택 안 함)인 경우
        else {
            iphoneOptions.classList.add('form-select2');
            galaxyOptions.classList.add('form-select2');
            caseOptions.classList.add('form-select3');
        }

    });
};

//share sns API
//Line
// var sns_title = "퐁케이스";
// var sns_br = "\n";
// var sns_summary = "핸드폰 쇼핑몰 퐁케이스~!"
// var sns_br = "\n";
// var sns_img = "/memberproductimg/shareimg.jpg";
// var link = "http://localhost:8080/";
// var url = "http://line.me/R/msg/text/?" + encodeURIComponent(sns_title + sns_br + sns_summary + sns_br + sns_link);


//카카오톡
    Kakao.init('d55e36cba95fba343ff2c3589281f46b'); 


function sendLink() {
    Kakao.Link.sendDefault({
        objectType: 'text',
        text: '간단한 JavaScript SDK 샘플과 함께 카카오 플랫폼 서비스 개발을 시작해 보세요.',
        link: {
            mobileWebUrl: 'https://developers.kakao.com',
            webUrl: 'https://developers.kakao.com'
        },
        serverCallbackArgs: { // 사용자 정의 파라미터 설정
            key: 'value'
        }
    });
}

