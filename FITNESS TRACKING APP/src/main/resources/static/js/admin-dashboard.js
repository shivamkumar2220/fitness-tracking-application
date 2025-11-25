document.addEventListener('DOMContentLoaded', () => {
    const chartCanvas = document.getElementById('userMixChart');
    if (!chartCanvas || !window.adminDashboardData) {
        return;
    }

    const data = window.adminDashboardData;
    const userMixChart = new Chart(chartCanvas, {
        type: 'bar',
        data: {
            labels: ['Admins', 'Users', 'Challenges', 'Workouts'],
            datasets: [
                {
                    label: 'Totals',
                    data: [data.adminCount, data.userCount, data.challengeCount, data.totalWorkouts],
                    backgroundColor: ['#2563eb', '#10b981', '#f97316', '#8b5cf6']
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: false }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: { precision: 0 }
                }
            }
        }
    });

    // Add a small doughnut visual for content approvals if a placeholder exists
    const contentCanvas = document.getElementById('contentApprovalChart');
    if (contentCanvas) {
        new Chart(contentCanvas, {
            type: 'doughnut',
            data: {
                labels: ['Approved', 'Pending'],
                datasets: [{
                    data: [data.approvedContent, data.pendingContent],
                    backgroundColor: ['#22c55e', '#facc15']
                }]
            },
            options: {
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });
    }
});

