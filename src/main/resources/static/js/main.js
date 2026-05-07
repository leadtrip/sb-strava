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

        const url = button.dataset.url;

        const body = {};

        if(button.dataset.type === 'activities') {
            body.totalPagesToSync = Number(
                document.getElementById('totalPagesToSync').value
            )

            if (document.getElementById('fromDate')) {
                body.fromDate = document.getElementById('fromDate').value;
            }
        }

        showSpinner(true);
        button.disabled = true;

        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        })
            .then(async response => {
                if (response.ok) {
                    return response.json();
                }

                const errorMessage = await response.text();
                throw new Error(errorMessage || 'An unknown error occurred');
            })
            .then(data => {
                showGlobalAlert(`Sync completed: ${data} items synced`, 'success');
            })
            .catch((error) => {
                showGlobalAlert(error.message, 'danger');
            })
            .finally(() => {
                showSpinner(false);
                button.disabled = false;
            });
    });
});

