function showSpinner(show = true) {
    const spinner = document.getElementById('spinner');
    if (spinner) {
        spinner.style.display = show ? 'inline-block' : 'none';
    }
}

function showGlobalAlert(message, type = 'info', timeout = 5000) {
    const alertBox = document.getElementById('globalAlert');
    const alertMessage = document.getElementById('globalAlertMessage');

    if (!alertBox || !alertMessage) return;

    alertBox.className = `alert alert-${type} alert-dismissible fade show mx-3 mt-3`;
    alertMessage.textContent = message;
    alertBox.classList.remove('d-none');

    // Auto-hide after timeout
    setTimeout(() => {
        alertBox.classList.remove('show'); // fade out
        setTimeout(() => alertBox.classList.add('d-none'), 200); // hide after fade
    }, timeout);
}



function hideGlobalAlert() {
    document.getElementById('globalAlert')?.classList.add('d-none');
}


document.addEventListener('DOMContentLoaded', function () {
    document.body.addEventListener('click', function (e) {
        if (e.target && e.target.id === 'syncBtn') {
            const totalPagesToSync = document.getElementById('totalPagesToSync')?.value;

            showSpinner(true);

            fetch('/activity/syncactivities', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: `totalPagesToSync=${encodeURIComponent(totalPagesToSync)}`
            })
                .then(response => response.json())
                .then(data => {
                    showGlobalAlert(`Synced ${data} rows successfully!`, 'success');
                })
                .catch(error => {
                    showGlobalAlert('Sync failed. Please try again.', 'danger');
                })
                .finally(() => {
                    showSpinner(false);
                });
        }
    });
});
