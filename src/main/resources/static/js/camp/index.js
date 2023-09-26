// 메뉴에 hover시 버스메뉴 애니메이션

const nav_li = document.querySelectorAll(".header-nav>.inner-wrap>ul>li");
       
nav_li.forEach((menuItem) => {
    const submenu = menuItem.querySelector('.sub-nav');
    const menu = menuItem.querySelector('a');
    menu.addEventListener('mouseenter', () => {
        menuItem.classList.add('on');
        // 마우스 호버 시 서브메뉴를 보이도록 클래스 추가
        submenu.classList.add('on');
    });
    menuItem.addEventListener('mouseleave', () => {
        menuItem.classList.remove('on');
        // 마우스가 벗어날 때 서브메뉴를 숨기도록 클래스 제거
        submenu.classList.remove('on');
    });
});

// 최상단으로 애니메이션
const scrollTopBtn = document.querySelector(".float-btn");

scrollTopBtn.addEventListener('click',function(){
    window.scrollTo({top: 0, behavior:'smooth'});
});


// 커스텀 셀렉트박스(sort1)

const label1 = document.querySelector('.sort1 .label');
const options1 = document.querySelectorAll('.sort1 .optionItem');

// 클릭한 옵션의 텍스트를 라벨 안에 넣음
const handleSelect1 = (item1) => {
  label1.parentNode.classList.remove('active');
  label1.innerHTML = item1.textContent;
}
// 옵션 클릭시 클릭한 옵션을 넘김
options1.forEach(option1 => {
	option1.addEventListener('click', () => handleSelect1(option1))
})

// 라벨을 클릭시 옵션 목록이 열림/닫힘
label1.addEventListener('click', () => {
    label2.parentNode.classList.remove('active');
  if(label1.parentNode.classList.contains('active')) {
  	label1.parentNode.classList.remove('active');
  } else {
  	label1.parentNode.classList.add('active');
  }
})

// 커스텀 셀렉트박스(.list-sorting)

const label = document.querySelector('.list-sorting .label');
const options = document.querySelectorAll('.list-sorting .optionItem');

// 클릭한 옵션의 텍스트를 라벨 안에 넣음
const handleSelect = (item) => {
  label.parentNode.classList.remove('active');
  label.innerHTML = item.textContent;
}
// 옵션 클릭시 클릭한 옵션을 넘김
options.forEach(option => {
	option.addEventListener('click', () => handleSelect(option))
})

// 라벨을 클릭시 옵션 목록이 열림/닫힘
label.addEventListener('click', () => {
    label1.parentNode.classList.remove('active');
    label2.parentNode.classList.remove('active');
  if(label.parentNode.classList.contains('active')) {
  	label.parentNode.classList.remove('active');
  } else {
  	label.parentNode.classList.add('active');
  }
})

// 커스텀 셀렉트박스(sort2)

const label2 = document.querySelector('.sort2 .label');
const options2 = document.querySelectorAll('.sort2 .optionItem');

// 클릭한 옵션의 텍스트를 라벨 안에 넣음
const handleSelect2 = (item2) => {
  label2.parentNode.classList.remove('active');
  label2.innerHTML = item2.textContent;
}
// 옵션 클릭시 클릭한 옵션을 넘김
options2.forEach(option2 => {
	option2.addEventListener('click', () => handleSelect2(option2))
})

// 라벨을 클릭시 옵션 목록이 열림/닫힘
label2.addEventListener('click', () => {
    label1.parentNode.classList.remove('active');
  if(label2.parentNode.classList.contains('active')) {
  	label2.parentNode.classList.remove('active');
  } else {
  	label2.parentNode.classList.add('active');
  }
})