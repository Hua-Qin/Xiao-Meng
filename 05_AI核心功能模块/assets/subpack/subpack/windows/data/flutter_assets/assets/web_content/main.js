/**
 * AI工作区主脚本
 * 提供基本功能和界面交互
 */

document.addEventListener('DOMContentLoaded', function() {
    // 设置年份
    const yearEl = document.getElementById('year');
    if (yearEl) {
        yearEl.textContent = new Date().getFullYear();
    }
    
    // 底部导航
    setupNavigation();

    // 代理测试按钮
    setupProxyTest();
    
    // 初始化数据存储
    setupStorage();
    
    // 初始化下载测试功能
    setupDownloadTests();
    
    // 添加页面过渡动画
    addPageTransition();
});

/**
 * 设置代理测试
 */
function setupProxyTest() {
    const proxyBtn = document.getElementById('proxy-test-btn');
    const proxyUrlInput = document.getElementById('proxy-url');
    const proxyResult = document.getElementById('proxy-result');
    const proxyIndicator = document.getElementById('proxy-indicator');

    if (!proxyBtn || !proxyUrlInput || !proxyResult || !proxyIndicator) {
        return;
    }

    proxyBtn.addEventListener('click', async function() {
        const url = proxyUrlInput.value.trim();
        if (!url) {
            showNotification('请输入测试 URL', 'warning');
            return;
        }

        proxyBtn.disabled = true;
        proxyBtn.textContent = '测试中...';
        proxyIndicator.classList.remove('active', 'error');
        proxyResult.textContent = '请求中...';

        try {
            const controller = new AbortController();
            const timeoutId = setTimeout(() => controller.abort(), 8000);

            const res = await fetch(url, { method: 'GET', signal: controller.signal });
            const text = await res.text();
            clearTimeout(timeoutId);

            proxyIndicator.classList.add('active');
            proxyResult.innerHTML = `状态: ${res.status} ${res.statusText}<pre>${escapeHtml(text.slice(0, 600))}</pre>`;
        } catch (e) {
            proxyIndicator.classList.add('error');
            const message = e.name === 'AbortError' ? '请求超时（8秒）' : e.message;
            proxyResult.textContent = '请求失败: ' + message;
            showNotification('代理测试失败: ' + message, 'error');
        } finally {
            proxyBtn.disabled = false;
            proxyBtn.textContent = '测试请求';
        }
    });
}

/**
 * 初始化本地存储系统
 */
function setupStorage() {
    const storageInput = document.getElementById('storage-input');
    const saveBtn = document.getElementById('save-btn');
    const clearBtn = document.getElementById('clear-btn');
    const savedContent = document.getElementById('saved-content');
    const savedTime = document.getElementById('saved-time');
    const storageIndicator = document.getElementById('storage-indicator');
    
    // 页面加载时读取已保存的数据
    loadSavedData();
    
    // 保存按钮点击事件
    saveBtn.addEventListener('click', function() {
        const text = storageInput.value.trim();
        if (text) {
            try {
                // 保存数据和时间戳到localStorage
                const data = {
                    content: text,
                    timestamp: new Date().toLocaleString()
                };
                
                localStorage.setItem('savedData', JSON.stringify(data));
                
                // 更新显示
                loadSavedData();
                storageInput.value = '';
                
                // 添加成功提示
                showNotification('数据已保存', 'success');
            } catch (e) {
                console.error('localStorage保存失败:', e);
                showNotification('保存失败: ' + e.message, 'error');
            }
        } else {
            showNotification('请输入内容后再保存', 'warning');
        }
    });
    
    // 清除按钮点击事件
    clearBtn.addEventListener('click', function() {
        try {
            localStorage.removeItem('savedData');
            savedContent.textContent = '无数据';
            savedTime.textContent = '无';
            storageIndicator.classList.remove('active');
            
            showNotification('数据已清除', 'success');
        } catch (e) {
            console.error('localStorage清除失败:', e);
            showNotification('清除失败: ' + e.message, 'error');
        }
    });
}

/**
 * 读取已保存的数据
 */
function loadSavedData() {
    try {
        const savedData = localStorage.getItem('savedData');
        const savedContent = document.getElementById('saved-content');
        const savedTime = document.getElementById('saved-time');
        const storageIndicator = document.getElementById('storage-indicator');
        
        if (savedData) {
            const data = JSON.parse(savedData);
            savedContent.textContent = data.content || '无数据';
            savedTime.textContent = data.timestamp || '无';
            storageIndicator.classList.add('active');
        } else {
            savedContent.textContent = '无数据';
            savedTime.textContent = '无';
            storageIndicator.classList.remove('active');
        }
    } catch (e) {
        console.error('读取localStorage数据失败:', e);
        document.getElementById('saved-content').textContent = '读取失败';
    }
}

/**
 * 显示通知消息
 * @param {string} message - 通知消息
 * @param {string} type - 通知类型 (success, error, warning)
 */
function showNotification(message, type) {
    // 检查是否已有通知，如果有则移除
    const existingNotification = document.querySelector('.notification');
    if (existingNotification) {
        document.body.removeChild(existingNotification);
    }
    
    // 创建通知元素
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    // 添加到页面
    document.body.appendChild(notification);
    
    // 动画效果
    setTimeout(() => {
        notification.classList.add('show');
    }, 10);
    
    // 3秒后移除
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => {
            if (notification.parentElement) {
                document.body.removeChild(notification);
            }
        }, 300);
    }, 3000);
}

/**
 * 底部导航切换
 */
function setupNavigation() {
    const navItems = document.querySelectorAll('.nav-item');
    const screens = document.querySelectorAll('.screen');

    if (!navItems.length || !screens.length) {
        return;
    }

    navItems.forEach((item) => {
        item.addEventListener('click', () => {
            const target = item.getAttribute('data-target');
            if (!target) return;

            navItems.forEach((btn) => btn.classList.remove('active'));
            item.classList.add('active');

            screens.forEach((screen) => {
                if (screen.getAttribute('data-screen') === target) {
                    screen.classList.add('active');
                } else {
                    screen.classList.remove('active');
                }
            });

            window.scrollTo(0, 0);
            addPageTransition();
        });
    });
}

/**
 * 简单转义，避免把响应内容当作HTML执行
 */
function escapeHtml(text) {
    return text
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

/**
 * 添加页面过渡动画
 */
function addPageTransition() {
    // 添加卡片动画
    const cards = document.querySelectorAll('.screen.active .card');
    cards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        
        setTimeout(() => {
            card.style.transition = 'opacity 0.3s ease, transform 0.3s ease';
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, 100 + (index * 100));
    });
}

/**
 * 初始化下载测试功能
 */
function setupDownloadTests() {
    const downloadTextBtn = document.getElementById('download-text-btn');
    const generateBlobBtn = document.getElementById('generate-blob-btn');
    const blobContentInput = document.getElementById('blob-content');
    const dynamicJsDownloadBtn = document.getElementById('dynamic-js-download-btn');
    const downloadIndicator = document.getElementById('download-indicator');
    const lastDownload = document.getElementById('last-download');
    
    // 普通文本文件下载
    downloadTextBtn.addEventListener('click', function() {
        try {
            const textContent = "这是一个测试文本文件\n创建时间: " + new Date().toLocaleString();
            const fileName = "test_" + new Date().getTime() + ".txt";
            
            downloadFile(textContent, fileName, 'text/plain');
            updateDownloadStatus(fileName);
        } catch (e) {
            console.error('文件下载失败:', e);
            showNotification('下载失败: ' + e.message, 'error');
        }
    });
    
    // Blob内容生成与下载
    generateBlobBtn.addEventListener('click', function() {
        try {
            const blobContent = blobContentInput.value.trim() || 
                                "这是一个由Blob生成的文件内容\n创建时间: " + new Date().toLocaleString();
            
            const fileName = "blob_" + new Date().getTime() + ".txt";
            
            // 创建Blob对象
            const blob = new Blob([blobContent], {type: 'text/plain'});
            
            // 创建blob URL
            const blobUrl = URL.createObjectURL(blob);
            
            // 下载文件
            if (window.downloadFile) {
                // 使用全局注入的下载函数（WebView添加的）
                window.downloadFile(blobUrl, fileName);
                updateDownloadStatus(fileName + " (通过WebView API)");
            } else {
                // 直接使用浏览器下载（Web测试时）
                downloadWithBrowserAPI(blob, fileName);
            }
            
            // 可选：清空输入框
            blobContentInput.value = '';
        } catch (e) {
            console.error('Blob下载失败:', e);
            showNotification('Blob下载失败: ' + e.message, 'error');
        }
    });
    
    // 纯JS动态下载
    dynamicJsDownloadBtn.addEventListener('click', function() {
        try {
            const content = "这是一个通过纯JS动态创建的测试文件内容\n用于验证底层拦截功能";
            const blob = new Blob([content], { type: 'text/plain' });
            const url = URL.createObjectURL(blob);
            
            const a = document.createElement('a');
            a.href = url;
            a.download = 'dynamic_js_test.txt';
            document.body.appendChild(a);
            a.click();
            
            setTimeout(() => {
                document.body.removeChild(a);
                URL.revokeObjectURL(url);
            }, 100);

            // 注意：由于下载被底层JS拦截，这里的updateDownloadStatus可能不会在拦截成功时立即获得反馈
            // 应用级的SnackBar通知是更可靠的成功指示
            updateDownloadStatus('dynamic_js_test.txt (动态触发)');

        } catch (e) {
            console.error('动态JS下载失败:', e);
            showNotification('动态JS下载失败: ' + e.message, 'error');
        }
    });
    
    /**
     * 使用浏览器原生API下载Blob
     * @param {Blob} blob - Blob对象
     * @param {string} fileName - 文件名
     */
    function downloadWithBrowserAPI(blob, fileName) {
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.style.display = 'none';
        a.href = url;
        a.download = fileName;
        document.body.appendChild(a);
        a.click();
        setTimeout(() => {
            document.body.removeChild(a);
            URL.revokeObjectURL(url);
        }, 100);
    }
    
    /**
     * 下载文本文件
     * @param {string} content - 文件内容
     * @param {string} fileName - 文件名
     * @param {string} mimeType - MIME类型
     */
    function downloadFile(content, fileName, mimeType) {
        const blob = new Blob([content], {type: mimeType});
        
        if (window.downloadFile) {
            // 使用全局注入的下载函数（WebView添加的）
            const blobUrl = URL.createObjectURL(blob);
            window.downloadFile(blobUrl, fileName);
        } else {
            // 直接使用浏览器下载（Web测试时）
            downloadWithBrowserAPI(blob, fileName);
        }
    }
    
    /**
     * 更新下载状态
     * @param {string} fileName - 文件名
     */
    function updateDownloadStatus(fileName) {
        lastDownload.textContent = fileName;
        downloadIndicator.classList.add('active');
        
        // 显示通知
        showNotification('文件下载成功: ' + fileName, 'success');
    }
} 
