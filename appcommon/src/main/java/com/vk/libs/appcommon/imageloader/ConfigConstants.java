/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.vk.libs.appcommon.imageloader;


public class ConfigConstants {

  public static final int KB = 1024;
  public static final int MB = 1024 * KB;

  private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

  /**40MB*/
  public static final int MAX_DISK_CACHE_SIZE = 40 * MB;
  public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
}
