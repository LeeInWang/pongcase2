document.addEventListener("DOMContentLoaded", function() {
    var acc = document.querySelectorAll(".accordion");

    acc.forEach(function(item) {
        item.addEventListener("click", function(event) {
            event.preventDefault(); // 기본 동작 방지
            event.stopPropagation(); // 이벤트 버블링 방지

            var panel = this.nextElementSibling;

            // 패널이 열려있는지 확인 후 토글
            if (panel.style.maxHeight) {
                panel.style.maxHeight = null;
            } else {
                closeAllPanels(); // 다른 패널 닫기
                this.classList.add("active"); // 활성화 클래스 추가
                panel.style.maxHeight = panel.scrollHeight + "px";
            }
        });
    });

    // 다른 패널 닫기
    function closeAllPanels() {
        acc.forEach(function(item) {
            item.classList.remove("active");
            var panel = item.nextElementSibling;
            panel.style.maxHeight = null;
        });
    }

    // 체크박스 클릭시 이벤트 버블링 방지
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener("click", function(event) {
            event.stopPropagation(); // 이벤트 버블링 방지
        });
    });
});




function getCheckedItemValueForUpdate() {
    var checkboxes = document.getElementsByName('boardFaqcheck');
    var checkedValue = null;
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            checkedValue = checkboxes[i].value;
            break;
        }
    }
    if (checkedValue !== null) {
        // 선택된 checkbox의 값이 여기에 저장됩니다.
        console.log("선택된 checkbox의 값: ", checkedValue);

        // 폼에 해당 값을 추가하여 컨트롤러로 전송하는 등의 작업을 수행할 수 있습니다.
        var form = document.getElementById('updateForm');
        var input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'boardFaqNumber'; // 수정 폼에 전달할 파라미터 이름
        input.value = checkedValue; // 선택된 체크박스의 값
        form.appendChild(input);

        // 수정 폼을 서버로 제출합니다.
        form.submit();
    } else {
        alert("수정할 항목을 선택하세요.");
    }
}
function deleteCheckedItems() {
    // 체크박스의 상태를 확인
    var checkboxes = document.getElementsByName('boardFaqcheck');
    var checkedValues = [];
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            checkedValues.push(checkboxes[i].value);
        }
    }

    // 체크된 항목이 있으면 서버로 전송
    if (checkedValues.length > 0) {
        var form = document.getElementById('deleteForm');
        for (var i = 0; i < checkedValues.length; i++) {
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'boardFaqNumber';
            input.value = checkedValues[i];
            form.appendChild(input);
        }
        form.submit();
    }
}

