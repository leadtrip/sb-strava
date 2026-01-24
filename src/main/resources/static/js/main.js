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
        const button = e.target;
        if (!button || button.dataset.action !== 'sync') return;

        const body = {
            totalPagesToSync: Number(
                document.getElementById('totalPagesToSync').value
            )
        };

        if (document.getElementById('fromDate')) {
            body.fromDate = document.getElementById('fromDate').value;
        }

        showSpinner(true);
        button.disabled = true;

        fetch('/activity/syncactivities', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => {
                showGlobalAlert(`Sync completed: ${JSON.stringify(data)}`, 'success');
            })
            .catch(() => {
                showGlobalAlert('Sync failed. Please try again.', 'danger');
            })
            .finally(() => {
                showSpinner(false);
                button.disabled = false;
            });
    });
});

