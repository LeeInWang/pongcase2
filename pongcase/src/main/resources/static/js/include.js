fetch('/arrow')
    .then(response => response.text())
    .then(html => {
        document.getElementById('quickmenu').innerHTML = html;
    })
    .catch(error => console.error('Error fetching HTML:', error));

fetch('/quick')
    .then(response => response.text())
    .then(html => {
        document.getElementById('arrow').innerHTML = html;
    })
    .catch(error => console.error('Error fetching HTML:', error));